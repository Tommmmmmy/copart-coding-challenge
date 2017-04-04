import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//code by Maoming Tang

public class AppropriateFacility {

    /**
     * define constant
     */
    private static final String ZIP_CODES = "zip_codes";
    private static final String ZIP_CODE = "zip_code";
    private static final String PLACE_HOLDER_ZIP = "${zipcode}";
    private static final String PLACE_HOLDER_DISTANCE = "${distance}";
    private static final String ZIP_DISTANCE_URL = "https://www.zipcodeapi.com/" +
            "rest/<api_key>/radius.csv/" + PLACE_HOLDER_ZIP + "/" + PLACE_HOLDER_DISTANCE + "/miles?minimal";
    private static final Integer MOST_TRY_TIMES = 30;

    /**
     * Using customerid to find customer's prefer choice
     * This function will query in mysql database
     * @param customerid
     * @return
     */
    public String findPrefer(String customerid) {
        MysqlConnector m = new MysqlConnector();
        Connection conn = m.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String prefer = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM prefer;");
            if (rs.next()) {
                prefer = rs.getString("prefer_id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.closeConnection();
        return prefer;
    }

    /**
     * check is there a facility locate in this zipcode
     * @param zipcode
     * @return
     */
    public Boolean isExistFacility(String zipcode) {
        MysqlConnector m = new MysqlConnector();
        Connection conn = m.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String facilityId = null;
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM facility WHERE zipcode = ?";
            PreparedStatement preState = conn.prepareStatement(query);
            preState.setString(1, zipcode);
            rs = preState.executeQuery();
            if (rs.next()) {
                facilityId = rs.getString("id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.closeConnection();
        return StringUtils.isNotBlank(facilityId);
    }

    /**
     * Find all zip codes within a given radius of a zip code
     * @param zipCode
     * @param distance
     * @return
     */
    public List<String> getClosestZip(String zipCode, String distance) {
        String url = ZIP_DISTANCE_URL.replace(PLACE_HOLDER_ZIP, zipCode)
                .replace(PLACE_HOLDER_DISTANCE, distance);
        String result = HttpGetAPI.getAPI(url);
        JSONObject json = JSONObject.fromObject(result);
        List<String> zipCodes = json.getJSONArray(ZIP_CODES);
        List<String> closeZip = new ArrayList<String>();
        for (String zipInfo : zipCodes) {
            JSONObject zipObject = JSONObject.fromObject(zipInfo);
            closeZip.add(zipObject.getString(ZIP_CODE));
        }
        return closeZip;
    }

    /**
     * TODO: This method should be replaced by rules engine
     * @param customerId
     * @param zipCode
     * @return
     */
    private String finalChoice(String customerId, String zipCode) {
        String finalZip = findPrefer(customerId);
        // if we can find customer's preference in database, then use it
        if (StringUtils.isNotBlank(finalZip)) {
            return finalZip;
        }
        // we cant find prefer in db, then we check is there a facility in the zipcode this customer specified
        if (isExistFacility(zipCode)) {
            return zipCode;
        }
        // There's no facility in specified zipcode, we try to find a closest place
        Integer distance = 3;
        Integer tryTime = 1;
        while (StringUtils.isBlank(finalZip) && tryTime <= MOST_TRY_TIMES) {
            List<String> zipCodes = getClosestZip(zipCode, distance.toString());
            if (CollectionUtils.isNotEmpty(zipCodes)) {
                finalZip = zipCodes.get(0);
            }
            distance++;
        }
        if (StringUtils.isNotBlank(finalZip)) {
            return finalZip;
        } else {
            // -1 means we fail to find appropriate zipcode
            return "-1";
        }
    }

    /**
     * This one is the interface provided for users
     * @param customerId
     * @param zipCode
     * @return
     */
    public String getFacility(String customerId, String zipCode) {
        String finalZip = finalChoice(customerId, zipCode);
        if (finalZip.equals("-1")) {
            return "We can't find an appropriate facility.";
        } else {
            ZipCodeLookup lookup = new ZipCodeLookup();
            return lookup.convertZipcode(finalZip);
        }
    }
}
