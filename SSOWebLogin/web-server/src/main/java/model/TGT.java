package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TGT {
    //用户信息
    public User user;
    //当前信任的url
    public Map<String,String> safeUrl = new HashMap<String, String>();

}
