
public class licenseKey {
  /**
   *  traverse from the end of the string 
   *  if the length of the StringBuilder is divisible by k append '-' else append 
   *  the character
   * @param s
   * @param k
   * @return
   */
  public String licenseKeyFormatting(String s, int k) {
    StringBuilder sb = new StringBuilder();
    for (int i = s.length() - 1; i >= 0; i--)
        if (s.charAt(i) != '-')
            sb.append(sb.length() % (k + 1) == k ? '-' : "").append(s.charAt(i));
    return sb.reverse().toString().toUpperCase();
  } 
}
