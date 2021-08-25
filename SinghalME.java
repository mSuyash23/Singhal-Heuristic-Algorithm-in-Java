import daj.Program;

import java.util.Arrays;
public class SinghalME extends Program {

    static char NoneOfTheAbove = 'N';
    static char RequestingTheCS = 'R';   //Requesting the Token;
    static char ExecutingTheCS = 'E';
    static char HoldingTheIdleToken = 'H';
    public static int num=0;
    protected static int[] TSN;
    protected static char[] TSV;
    public int[] SN;
    public int ID;
    public char[] SV; static boolean FirstTime=true;
    public boolean HaveToken;

    public SinghalME(int Proc_ID,int NumOfSites)
    {

        if(FirstTime)
        {
            //Initialisation of Token and as no site have messages to receive;
            TSN=new int[NumOfSites];
            TSV=new char[NumOfSites];
            Arrays.fill(TSN,0);
            Arrays.fill(TSV,'N');
            FirstTime=false;
        }
        //Initializing every Site;
        SN = new int[NumOfSites];
        SV = new char[NumOfSites];
        HaveToken=false;
        ID = Proc_ID;
        for(int i=0;i<NumOfSites;i++)
        {
            if(i>=Proc_ID)
            {
                SN[i]=0; SV[i]=NoneOfTheAbove;
            }
            else
            {
                SN[i]=0; SV[i]=RequestingTheCS;
            }
        }
        if(Proc_ID==0)
        {
            HaveToken=true;
            SV[Proc_ID]=HoldingTheIdleToken;
        }
    }
    // Main function of the program;
    @Override
    public void main(){
        while (true)
        {
            String ReqToken;
            ReqToken = "";
            if(Math.random() <= 0.5)
                ReqToken = "Y";
            if(ReqToken.equals("Y"))
            {
                SV[ID]=RequestingTheCS;
                if(HaveToken)
                {
                    EnterCS();
                    UpdateLocalInfo();
                }
                else
                {
                    SN[ID]=SN[ID]+1;
                    SendRequest();
                    while (SV[ID]==RequestingTheCS)
                    {
                        int i = in().select();
                        MESSAGE MsG = (MESSAGE) in(i).receive();
                        WhenReceived(MsG);
                    }
                }
            }
            while (SV[ID]==HoldingTheIdleToken)
            {
                int i = in().select();
                MESSAGE MsG = (MESSAGE) in(i).receive();
                WhenReceived(MsG);
            }
            break;
        }
    }

    public void SendRequest()
    {
        int j=0;
        for(int i=0;i<SV.length;i++)
        {
            if((SV[i]==RequestingTheCS)&& i!=ID)
            {
                //Sending requests to sites which have SV[i]==R;
                out(i).send(new MESSAGE(ID,SN[ID],false));
            }
            else if(i!=ID)
                j=j+1;
        }
    }

    //Updating any outdated info from the messages received and taking appropriate action based on SV[ID];
    public void WhenReceived(MESSAGE MsG)
    {
        if(MsG.IsToken)
        {
            HaveToken=true;
            EnterCS();
            UpdateLocalInfo();
            return;
        }
        if (SN[MsG.Proc_Num] >= MsG.Sn) {
            return;
        }
        SN[MsG.Proc_Num]=MsG.Sn;
        if(SV[ID]==NoneOfTheAbove)
            SV[MsG.Proc_Num]=RequestingTheCS;
        else if(SV[ID]==RequestingTheCS)
        {
            if(SV[MsG.Proc_Num]!=RequestingTheCS)
            {
                SV[MsG.Proc_Num]=RequestingTheCS;
                MESSAGE mSg = new MESSAGE(ID,SN[ID],false);
                out(MsG.Proc_Num).send(mSg);
            }
        }
        else if(SV[ID]==ExecutingTheCS)
            SV[MsG.Proc_Num]=RequestingTheCS;
        else if(SV[ID]==HoldingTheIdleToken)
        {
            SV[MsG.Proc_Num]=RequestingTheCS;
            TSV[MsG.Proc_Num]=RequestingTheCS;
            TSN[MsG.Proc_Num]= MsG.Sn;
            SV[ID]=NoneOfTheAbove;
            HaveToken=false;
            out(MsG.Proc_Num).send(new MESSAGE(ID,SN[ID],true));
        }

    }

    void EnterCS()
    {
        SV[ID]=ExecutingTheCS;
        num++;
    }
    void UpdateLocalInfo()
    {
        SV[ID]=NoneOfTheAbove; TSV[ID]=NoneOfTheAbove;
        for(int i=0;i<SV.length;i++)
        {
            if(SN[i] > TSN[i])
            {
                TSV[i]=SV[i];
                TSN[i]=SN[i];
            }
            else
            {

                SV[i]=TSV[i];
                SN[i]=TSN[i];
            }
        }
        if(SendToken()==-1)
            SV[ID]=HoldingTheIdleToken;
    }

    public int SendToken()
    {
        int j=0;
        for(int i=0;i<SV.length;i++)
        {
            if(SV[i]==RequestingTheCS && i!=ID)
            {
                MESSAGE MsG = new MESSAGE(ID,SN[ID],true);      //Sending the token to the site S(i) for which i is minimum;
                HaveToken=false;
                out(j).send(MsG);
                return 0;
            }
            else
                j++;
        }
        return -1;
    }
    public String getText() {
        String msgString = "";
        msgString += "State: " + Arrays.toString(SV) + "\n";
        msgString += "SeqNum: " + Arrays.toString(SN) + "\n";
        msgString += "CSVariable num :" + num + "\n";
        msgString+="TSN :" + Arrays.toString(TSN);

        return msgString;
    }
}
