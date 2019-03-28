Cucumber and Selenium for Mass

To run all the scenerio in the Feature file :
    mvn test -Dcucumber.options="src/test/resources/hellocucumber/mass.feature"

To run the specific scenerio from the feature file:

mvn test 
-DCucumber.options="src/test/resources/hellocucumber/mass.feature"
 -DCucumber.options="--tags @selectDatacenter"
