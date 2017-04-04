
public class strToInt {
  /**
   * use a boolean variable to mark if the number is negative
   * iterate the string use the character minus the '0' to get the int value of each char
   * return the result
   * @param str
   * @return
   */
  public int myAtoi(String str) {
    str = str.trim();
    boolean isNeg = false;
    int result = 0;
    for(int i=0; i<str.length(); i++) {
        if(i == 0 && (str.charAt(i) == '+' || str.charAt(i) == '-')) isNeg = str.charAt(i) == '-' ? true : false;
        else if(str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            int cur = str.charAt(i) - '0';
            if(result > (Integer.MAX_VALUE - cur) /10) return isNeg? Integer.MIN_VALUE : Integer.MAX_VALUE;
            else {
                result = result * 10 + cur;
            }
        }
        else return isNeg? -result : result;
    }
    return isNeg? -result: result;
}
}
