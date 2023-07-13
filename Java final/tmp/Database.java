package tmp;


import java.util.*;

import Member;

public class Database {
    private ArrayList<Member> memberinfo_;

    Database()
    {
        memberinfo_ = new ArrayList<Member>();
    }

    public ArrayList<Member> getMemberinfo()
    {
        return (memberinfo_);
    }

    public void setMemberinfo(Member member)
    {
        this.memberinfo_.add(member);
    }

}
