import daj.Message;
public class MESSAGE extends Message {
    int Proc_Num; int Sn; boolean IsToken;
    public MESSAGE(int Proc_ID,int ProcSeqNum,boolean ContainsToken)
    {
        this.Proc_Num=Proc_ID;
        this.Sn=ProcSeqNum;
        this.IsToken=ContainsToken;
    }

    public String getText()
    {
        String ChannelContent = "Site ID:" +
                Proc_Num + "\n" + "Seq_Num:" + Sn + "\n" +
                "ContainsToken:" + IsToken + "\n";
        return ChannelContent;


    }

}
