package com.example.sunwo.money_book;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MySMSReceiver extends BroadcastReceiver {

    DBExpense dbExpense;
    public MySMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d(TAG,"onReceive() 호출됨.");
        // 수신되었을 때 호출되는 콜백 메서드
        // 매개변수 intent의 액션에 방송의 '종류'가 들어있고
        //         필드에는 '추가정보' 가 들어 있습니다.

        // SMS 메시지를 파싱합니다.
        Bundle bundle = intent.getExtras();
        String phoneNumber = ""; // 출력할 문자열 저장
        String smsMessage = ""; // 출력할 문자열 저장
        if (bundle != null) { // 수신된 내용이 있으면
            // 실제 메세지는 Object타입의 배열에 PDU 형식으로 저장됨
            Object [] pdus = (Object[])bundle.get("pdus");

            SmsMessage[] msgs
                    = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                msgs[i] = SmsMessage
                        .createFromPdu((byte[]) pdus[i]);
                phoneNumber += msgs[i].getOriginatingAddress();
            }

            if(phoneNumber.equals("15881688")){

                dbExpense = new DBExpense(context.getApplicationContext(), "money_ex.db", null, 1);

                for (int i = 0; i < msgs.length; i++) {
                    // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                    msgs[i] = SmsMessage
                            .createFromPdu((byte[]) pdus[i]);
                    smsMessage += msgs[i].getMessageBody().toString();
                }
                KBSMSparse(smsMessage,dbExpense);
            }else if(phoneNumber.equals("15447200")) {

                dbExpense = new DBExpense(context.getApplicationContext(), "money_ex.db", null, 1);

                for (int i = 0; i < msgs.length; i++) {
                    // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                    msgs[i] = SmsMessage
                            .createFromPdu((byte[]) pdus[i]);
                    smsMessage += msgs[i].getMessageBody().toString();
                }
                SHSMSparse(smsMessage,dbExpense);
            }

        }
    } // end of onReceive

    public void KBSMSparse(String msgstr,DBExpense dbExpense){
        String[] extract, transformexpense, transformdate = null;
        String expenseString = "";
        int expenseInt;
        extract = msgstr.split("\n");
        //msgs[i].getMessageBody().toString()
        transformexpense = extract[3].split(",");
        for (int i = 0; i < transformexpense.length; i++) {
            expenseString += transformexpense[i];
        }
        expenseInt = Integer.parseInt(expenseString.substring(0, expenseString.length() - 1));

        transformdate = extract[2].split(" ");
        String dateString = "";
        String dateString2 = "2016";
        dateString = transformdate[0];
        transformdate = dateString.split("/");
        dateString2 += transformdate[0] + transformdate[1];
        //Toast.makeText(context, msgstr, Toast.LENGTH_LONG).show();


        dbExpense.insert("insert into MONEY_EX values(null, " + expenseInt + ", '" + "기타" + "',"
                + Integer.parseInt(dateString2) + ", '" + "카드" + "' , '" + extract[4] + "');");
    }

    public  void SHSMSparse(String msgstr,DBExpense dbExpense){
        String[] extract,transformexpense,transformdate=null;
        String expenseString="";
        int expenseInt;
        msgstr=msgstr.split("\n")[1];

        extract = msgstr.split(" ");
        transformexpense=extract[4].split(",");
        for(int i=0;i<transformexpense.length;i++){
            expenseString+=transformexpense[i];
        }
        expenseInt = Integer.parseInt(expenseString.substring(0, expenseString.length() - 1));

        String dateString = "";
        String dateString2 = "2016";
        dateString = extract[2];
        transformdate = dateString.split("/");
        dateString2 += transformdate[0] + transformdate[1];

        dbExpense.insert("insert into MONEY_EX values(null, " + expenseInt + ", '" + "기타" + "',"
                + Integer.parseInt(dateString2) + ", '" + "카드" + "' , '" + extract[5] + "');");

    }
}


