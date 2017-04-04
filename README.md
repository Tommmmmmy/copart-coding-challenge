# copart-coding-challenge

CodingChallenges

Group Member:

Feiyue Wu fxw160230@utdallas.edu

Maoming Tang  mxt162330@utdallas.edu

<1> Most Appropriate Facility: code by Maoming Tang

Firstly, I will use a customer’s ID to query data in mysql. If I find a preference, I return it.

Then check the postal code in mysql. If I can find a facility locate in the specified zipcode, then return it. If I can’t find it, then try to find the closest one. For this part, I use API in http://www.zipcodeapi.com/ . The API will help me to find all zip codes within a given radius of a zip code. So in my code, there’s a loop to increase radius until I can find a zipcode which Copart facilities locate in. This loop has MAX_TRY_TIMES setting to avoid overtime proceeding.

To improve it, I will use cache to optimize multi-users scenario.

<2> Coding Exercises - Problem statements (Node.js or Ruby)(30 points)
code by Maoming Tang
I use Ruby to finish these tasks:
1.connect database table
2.read and extract data from csv file
3.get information from google api(use a hashset to prevent a location from calling twice)
4.insert all information into table(including lat and lng)

<3> Extract Text from PDF/Image (20 points)
code by Maoming Tang
I use aspose api and tess4j to parse pdf and image respectively.
To apply them, you have to download jar files and import them into the project.


<4> License Keys: code by Feiyue Wu

I traverse the string from the end and use StringBuilder to append the character. Each time, the length of the StringBuilder would be compared with the required
length k.

<5> Variation of Most Appropriate Yard: code by Feiyue Wu

First I use map to store each city with its coordinates.

Then I use priority queue to store each city point. The order of them would be the distance to the target city. The farthest one would be on the top of the queue. 

After traverse all data, use the list to store the result and return it.
 
<6> Covert String to Integer: code by Feiyue Wu

I use a boolean variable to mark if the number is negative
Then I iterate the string use the character minus the '0' to get the int value of each char
return the result
