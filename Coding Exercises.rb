require 'csv'    
require 'set'
require "dbi"

mapPrefix = String.new("http://maps.google.com/maps/api/geocode/json?address=")
begin
     # connect to the MySQL server
     dbh = DBI.connect("DBI:Mysql:TESTDB:localhost", 
	                    "testuser", "test123")
     # create a table containing 24 columns including columns 'lat' and 'log'
     sth = dbh.do("CREATE TABLE data (......)")
     dbh.commit
rescue DBI::DatabaseError => e
     puts "An error occurred"
     puts "Error code:    #{e.err}"
     puts "Error message: #{e.errstr}"

# create a hashset so that no locations will be called twice
set = Set.new;
# reading data from csv file
CSV.foreach("VehicleDetails.csv", :headers => true) do |row|
	 ary = Array.new(22)
	 ary[0] = row['Lot URL']
	 ary[1] = row['Lot Number']
	 ary[2] = row['Est. Retail Value']
	 ary[3] = row['Year']
	 ary[4] = row['Make']
	 ary[5] = row['Model']
	 ary[6] = row['Expected Sale Date']
	 ary[7] = row['Engine Type']
	 ary[8] = row['State of Ownership Doc Type']
	 ary[9] = row['Ownership Doc Type']
	 ary[10] = row['Odometer Description']
	 ary[11] = row['Cylinders']
	 ary[12] = row['VIN']
	 ary[13] = row['Grid/Row']
	 ary[14] = row['Odometer Reading']
	 ary[15] = row['Damage Description']
	 ary[16] = row['Current Bid']
	 ary[17] = row['My Bid']
	 ary[18] = row['Item Number']
	 ary[19] = row['Location']
	 ary[20] = row['Sale Status']
	 ary[21] = row['Repair Estimate']
	 #see if the set contains the location or not
	 if !(set.include?(ary[19]))
	 	set.add(ary[19])
	 	values = ary[19].split(" - ")
	 	address = String.new(values[1] + ", " + values[0])
	 	completeAddress = String.new(mapPrefix + address)
	 	parsed = JSON.parse(completeAddress) 
	 	#get the coordinate information
	 	lat = String.new(parsed["results"]["geometry"]["location"]["lat"])
	 	lng = String.new(parsed["results"]["geometry"]["location"]["lng"])
	 	#we have 24 ?s in the statement. I use ...... for convenience even though it is not valid
	 	#inserting information into the table data including lat and lng
	    sth = dbh.prepare("INSERT INTO data VALUES (?,?,?,?,?,?,?,?....,?)")
	    # 22 and 23 represents the index of column 'lat' and 'lng'
	    sth.execute(ary[0], ary[1], ....... ary[21], ary[22], ary[23])
	    sth.finish
	    dbh.commit

end
ensure
     # disconnect from server
     dbh.disconnect if dbh
end

#This is written in Java for the convenience to get google API
def getAPI(url)
	String result = "";
        BufferedReader in = null;
        String urlNameString = url;
        URL realUrl = new URL(urlNameString);
        URLConnection connection = realUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.connect();
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
                result += line;    
           
        return result;
end