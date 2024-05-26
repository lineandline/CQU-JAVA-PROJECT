package cache;

import model.TGT;
import model.UserInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SelfCache {
    public static HashMap<String,String> stCache = new HashMap<String,String>();
    public static HashMap<String, TGT>tgtCache = new HashMap<String,TGT>();
    public static HashMap<String, Date>web1Cache = new HashMap<String, Date>();
    public static HashMap<String, Date>web2Cache = new HashMap<String, Date>();
    public static ArrayList<UserInfo> web1User = new ArrayList<UserInfo>();
}
