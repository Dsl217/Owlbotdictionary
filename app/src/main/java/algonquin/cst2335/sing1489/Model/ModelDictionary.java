package algonquin.cst2335.sing1489.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModelDictionary {

//class for storing values which will come from Api
    private List<Definition> definitions = null;

    private String word;

    @Override
    public String toString() {
        return "ModelDictionary{" +
                "definitions=" + definitions +
                ", word='" + word + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                '}';
    }

    private String pronunciation;


    public static class Definition {

        private String type;

        private String definition;

        private String example;

        @Override
        public String toString() {
            return "Definition{" +
                    "type='" + type + '\'' +
                    ", definition='" + definition + '\'' +
                    ", example='" + example + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", emoji='" + emoji + '\'' +
                    '}';
        }

        private String imageUrl;

        private String emoji;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getEmoji() {
            return emoji;
        }

        public void setEmoji(String emoji) {
            this.emoji = emoji;
        }

        /**
         * @param jsonObject
         * @return it returns Definition into string from jason object as b
         */
        public static ModelDictionary.Definition fromJson(JSONObject jsonObject) {
            //declare ModelDictionary.Definition() as b
            ModelDictionary.Definition b = new ModelDictionary.Definition();
            // Deserialize json into object fields
            //getting strings from the jasonObject
            //surrounded by try catch to catch exception if something mishappen
            try {
                b.definition = jsonObject.getString("definition");
                b.imageUrl = jsonObject.getString("image_url");
                b.example = jsonObject.getString("example");
                b.type = jsonObject.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            // Return new object
            return b;
        }

        public static ArrayList<Definition> fromJson(JSONArray jsonArray) {
            JSONObject jsonObject;
            ArrayList<Definition> definitions = new ArrayList<Definition>(jsonArray.length());
            // Process each result in json array, decode and convert to business object
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                Definition business = Definition.fromJson(jsonObject);
                if (business != null) {
                    definitions.add(business);
                }
            }

            return definitions;
        }
    }
}
