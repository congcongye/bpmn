package com.wosai.sqb.storemap;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 15/12/1.
 */
public class MainTest {


    public static void main(String [] args){
        ProcessBpmn processBpmn= new ProcessBpmn();
        DataProcess dataProcess =new DataProcess();
//        Map<String,List<String>> map= processBpmn.process(); //analyse the order of file
//        processBpmn.writeProcessOrder(map,"/Users/ycc/Desktop/processOrder.txt");
//        processBpmn.getSouceOutgoingById("_9675220a-2142-4bc6-9168-ff931dd6dbe3");
//        Map<String,Object> result =new HashMap<String,Object>();
//        result.put("type","");
//        int i=0;
//        String id ="StartEvent_1";
//        while(result.containsKey("type")&&!result.get("type").equals("end")){

//            result =  processBpmn.getNext("ExclusiveGateway_1","No");
//            System.out.println("nextid: "+ result.get("nextid")+"  ; type: "+ result.get("type"));
////            id =(String)result.get("nextid");
//        Collection<String> input =processBpmn.getInputData("Task_2",1);
//        Collection<String> output =processBpmn.getInputData("Task_2",2);
//        System.out.println("输入: "+input.size());
//        System.out.println("输出: "+output.size());
//            List<String> result =  dataProcess.getAttributeByTableName("bpmnOrder");
//        }
          String temp=processBpmn.getStateByTaskId("StartEvent_1");
          Map<String,String> map=new HashMap<String,String>();
          map=processBpmn.taskAndState;
          for(Map.Entry<String,String> entry:map.entrySet()){
              System.out.println("key: "+entry.getKey()+" ; key: "+entry.getValue());
          }

    }

}
