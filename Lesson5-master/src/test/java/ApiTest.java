import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;


public class ApiTest {

    public static String lastEpisode;
    public static String lastChar;
    public static String lastCharEpisode;
    public static String lastCharLocation;
    public static String lastCharSpecies;
    public static String mortySpecies;
    public static String mortyLocation;

    @Tag("1api")
    @Test
    @DisplayName("Последний эпизод с Морти")
    public void test1() {
        Response response1 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/character/2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String episode;
        List<Object> episodeList = new JSONObject(response1.body().asString()).getJSONArray("episode").toList();
        episode = episodeList.get(episodeList.size() - 1).toString();
        mortyLocation = new JSONObject(response1.body().asString()).getJSONObject("location").getString("name").toString();
        mortySpecies = new JSONObject(response1.body().asString()).getString("species").toString();
        System.out.println("Ссылка на последний эпизод: " + episode);
        lastEpisode = (episode.substring(episode.lastIndexOf("/")).substring(1));
        System.out.println("Последний эпизод " + lastEpisode);
    }

    @Tag("2api")
    @Test
    @DisplayName("Последний персонаж")
    public void test2() {
        Response response2 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/episode/" + lastEpisode)
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<Object> characterList = new JSONObject(response2.body().asString()).getJSONArray("characters").toList();
        lastChar = characterList.get(characterList.size() - 1).toString();
        lastCharEpisode = lastChar.substring(lastChar.lastIndexOf("/")).substring(1);
        System.out.println("Ссылка на последнего персонажа в последнем эпизоде " + lastChar);
    }

    @Tag("3api")
    @Test
    @DisplayName("Местонахожение и раса последнего персонажа")
    public void test3() {
        Response response3 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/character/" + lastCharEpisode)
                .then()
                .statusCode(200)
                .extract()
                .response();

        lastCharLocation = new JSONObject(response3.body().asString()).getJSONObject("location").getString("name").toString();
        lastCharSpecies = new JSONObject(response3.body().asString()).getString("species").toString();
        System.out.println("Местонахожение последнего персонажа " + lastCharLocation + ", Раса последнего персонажа " + lastCharSpecies);
    }


}