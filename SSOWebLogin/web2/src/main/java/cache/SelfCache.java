package cache;

import lombok.Data;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

@Data

public class SelfCache {
    public static HashMap<String, Date> userLoginMessage = new HashMap<String, Date>();
    public static HashMap<String, HttpSession> sessionCache = new HashMap<String,HttpSession>();
}
