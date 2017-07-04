package user;

import controller.LogInController.ACCESS;
import static controller.LogInController.ACCESS.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import model.UserModel;

public class UserSearcher {

    public static ACCESS search(UserModel user) {
        ACCESS state = EXCEPTION;
        String result = "";
        String resultPassword = "";
        UserModel wantedUser = new UserModel();
        Properties prop = new Properties();
        InputStream input = null;
        int amount;

        try {
            input = new FileInputStream("userData.properties");
            prop.load(input);		//reads a property list from the input byte stream
            amount = prop.size() / 2 + 1;
            for (int i = 1; i < amount; i++) {
                result = prop.getProperty("user" + i, "lack"); //Searches for the property with the specified key in this property list

                if (result.equals(user.getUserName())) {
                    resultPassword = prop.getProperty("user" + i + "pass");		//Returns:the value in this property list with the specified key value

                    if (resultPassword.equals(user.getPassword())) {
                        wantedUser.setUserName(result);
                        wantedUser.setPassword(resultPassword);
                        state = GRANTED;
                    } else {
                        state = WRONG_PASSWORD;
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return state;
    }
}
