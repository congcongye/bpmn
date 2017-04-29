package com.wosai.sqb.storemap;

import java.io.*;
import java.util.*;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.ActivityImpl;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.DataInput;
import org.camunda.bpm.model.bpmn.instance.DataOutput;
import org.camunda.bpm.model.xml.Model;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


/**
 * Created by ycc on 17/3/31.
 */
public class ProcessBpmn {
    String filePath = "order_process.bpmn";
    BpmnModelInstance modelInstance=null;
    Collection<ModelElementInstance> sequenceInstances=null;
    Collection<ModelElementInstance> startInstances=null;
    Map<String,List<String>> processOrder = null;
    Model model =null;
    ModelElementType taskType = null;
    ModelElementType exclusiveGatewayType =null;
    ModelElementType startType =null;
    ModelElementType endType =null;
    ModelElementType sequenceType = null;
    ModelElementType dataInputType = null;
    ModelElementType dataOutputType = null;
    ModelElementType dataObjectType = null;
    Map<String,String> taskAndState =null;

    public ProcessBpmn(){
        File file = new File(filePath);
        modelInstance = Bpmn.readModelFromFile(file);
        model =modelInstance.getModel();
        taskType = model.getType(Task.class);
        exclusiveGatewayType =model.getType(ExclusiveGateway.class);
        startType =model.getType(StartEvent.class);
        endType =model.getType(EndEvent.class);
        sequenceType =model.getType(SequenceFlow.class);// sequence type
        dataInputType = model.getType(DataInput.class);
        dataOutputType =model.getType(DataOutput.class);
        dataObjectType =model.getType(DataOutput.class);
        process();
        insureState();
    }



    /**
     * find all sequences and search sequences one by one
     * @return
     */
    public Map<String,List<String>> process(){

        sequenceInstances = modelInstance.getModelElementsByType(sequenceType); //all sequenceType
        processOrder= new HashMap<String,List<String>>();
        /**
         * search all sequences and record all source id and target id
         */
        for(ModelElementInstance sequence:sequenceInstances){
            String sourceId = sequence.getAttributeValue("sourceRef");
            String targetId = sequence.getAttributeValue("targetRef") ;
            List<String> temp=null;
            if(processOrder.containsKey(sourceId)) {
                temp =processOrder.get(sourceId);
            }else {
                temp =new ArrayList<String>();
            }
            temp.add(targetId);
            processOrder.put(sourceId,temp);
        }
        return processOrder;
    }

    /**
     * write the processOrder into the text
     * @param map
     * @param targetFilePath
     */
    public void writeProcessOrder(Map<String,List<String>> map,String targetFilePath){
        try {
            File file =new File(targetFilePath);
            if(!file.exists()){
                File dir =new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outputStream =new FileOutputStream(file);
            String result="";
            ObjectMapper mapper = new ObjectMapper();
            result=mapper.writeValueAsString(map);
            outputStream.write(result.getBytes());
            if (outputStream!=null){
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read String,and convert to map
     * @param sourceFilePath
     * @return
     */
    public Map<String,List<String>> readOrderFromText(String sourceFilePath){
        Map<String,List<String>> map =new HashMap<String,List<String>>();
        StringBuffer sb=new StringBuffer();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        try {
            String data=null;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)));
            while((data=br.readLine())!=null){
                sb.append(data);
            }
            result= mapper.readValue(sb.toString(), new TypeReference<Map<String, List<String>>>(){});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get start event id
     * @return
     */
    public List<String> getStartEvent(){
        List<String> start =new ArrayList<String>();
        startInstances = modelInstance.getModelElementsByType(startType); //all startEvent
        for(ModelElementInstance model:startInstances){
            start.add(model.getAttributeValue("id"));
        }
        return start;
    }


    /**
     * get outgoing of sequence source
     * @param id
     * @return
     */
    public Collection<SequenceFlow> getSouceOutgoingById(String id){
        Collection<SequenceFlow> outgoing =null;
        SequenceFlow flow =(SequenceFlow)modelInstance.getModelElementById(id);
        FlowNode source = flow.getSource();
        outgoing =source.getOutgoing();
        return outgoing;
    }

    /**
     * get outgoing of sequence target
     * @param id
     * @return
     */
    public Collection<SequenceFlow> getTargetOutgoingById(String id){
        Collection<SequenceFlow> outgoing =null;
        SequenceFlow flow =(SequenceFlow)modelInstance.getModelElementById(id);
        FlowNode source = flow.getTarget();
        outgoing =source.getOutgoing();
        return outgoing;
    }

    /**
     * get incoming of sequence target
     * @param id
     * @return
     */
    public Collection<SequenceFlow> getTargetIncomingById(String id){
        Collection<SequenceFlow> outgoing =null;
        SequenceFlow flow =(SequenceFlow)modelInstance.getModelElementById(id);
        FlowNode source = flow.getTarget();
        outgoing =source.getIncoming();
        return outgoing;
    }

    /**
     * get incoming of sequence source
     * @param id
     * @return
     */
    public Collection<SequenceFlow> getSourceIncomingById(String id){
        Collection<SequenceFlow> outgoing =null;
        SequenceFlow flow =(SequenceFlow)modelInstance.getModelElementById(id);
        FlowNode source = flow.getSource();
        outgoing =source.getIncoming();
        return outgoing;
    }

    /**
     *  according to the process id to decide which process should be executed next.
     *  conditionFlag: Yes / No
     */
    public Map<String,Object> getNext(String id,String conditionFlag){
        Map<String,Object> result =new HashMap<String,Object>();
        ModelElementInstance e = modelInstance.getModelElementById(id);
        ModelElementType et =e.getElementType();
        if(et.equals(startType)){  // no conditions to go to next events
//            List<String> nextids=processOrder.get(e.getAttributeValue("id"));
            Collection<SequenceFlow> outgoings = ((StartEvent)e).getOutgoing();
            String nextid = "";
            for (SequenceFlow sq : outgoings) { //only take account one way
                nextid = sq.getAttributeValue("targetRef");
            }
            result.put("nextid",nextid);
            result.put("type","start");
            return result;
        }else if(et.equals(endType)){
            result.put("type","end");
            return result;
        }else if(et.equals(exclusiveGatewayType)){
            ExclusiveGateway exclusiveGateway =(ExclusiveGateway)e;
            Collection<SequenceFlow> outgoings =exclusiveGateway.getOutgoing();
            for(SequenceFlow sq:outgoings){
                if(sq.getAttributeValue("name").equals(conditionFlag)){ // if conditionflag equals sq's name
                    String target =sq.getAttributeValue("targetRef");
                    result.put("type","exclusive");
                    result.put("nextid",target);
                    return result;
                }
            }
        }else  if(et.equals(taskType)) {
            String name = e.getAttributeValue("name").toString();// the name of the action
            ActivityImpl activity = (ActivityImpl) e;
            Collection<DataInputAssociation> dataInputAssociations = activity.getDataInputAssociations();
            List<String> input = new ArrayList<String>();
            List<String> output = new ArrayList<String>();
            for (DataInputAssociation dataInputAssociation : dataInputAssociations) {
                String sourceRef = dataInputAssociation.getAttributeValue("sourceRef");
                String targetRef = dataInputAssociation.getAttributeValue("targetRef");
                if(sourceRef==null || targetRef==null){
                    break;
                }
                ModelElementInstance tempsource = modelInstance.getModelElementById(sourceRef);
                ModelElementInstance temptarget = modelInstance.getModelElementById(targetRef);
                ModelElementType tempStp = tempsource.getElementType();
                ModelElementType tempTtp = temptarget.getElementType();
                if (tempStp.equals(dataObjectType)) {
                    input.add(tempsource.getAttributeValue("name"));
                }
                if (tempTtp.equals(dataObjectType)) {
                    output.add(temptarget.getAttributeValue("name"));
                }
            }
            Collection<SequenceFlow> outgoings = activity.getOutgoing();
            String nextid = "";
            for (SequenceFlow sq : outgoings) { //only take account one way
                nextid = sq.getAttributeValue("targetRef");
            }
            result.put("type", "task");
            result.put("inputData", input);
            result.put("outputData", output);
            result.put("nextid", nextid);
            return result;
        }
        return result;
    }

    /**
     * 根据id获得输入或者输出
     * @param id
     * @param outflag  1 输入  2输出
     */
    public List<String> getInputData(String id,int outflag){
        ModelElementInstance e = modelInstance.getModelElementById(id);
        ActivityImpl activity = (ActivityImpl) e;
        Collection<DataInputAssociation> dataInputAssociations = activity.getDataInputAssociations();
        Collection<DataOutputAssociation> dataOutputAssociations =activity.getDataOutputAssociations();
        List<String> input = new ArrayList<String>();
        List<String> output = new ArrayList<String>();
        for (DataInputAssociation dataInputAssociation : dataInputAssociations) {
            Collection<ItemAwareElement> sourceRefs = dataInputAssociation.getSources();
            for(ItemAwareElement itemAwareElement:sourceRefs){
                String sourceId =itemAwareElement.getId();
                 String tempStp =itemAwareElement.getElementType().getTypeName();
                ModelElementInstance tempSource = modelInstance.getModelElementById(sourceId);
                if (tempStp.equals("dataObject")) {
                    input.add(tempSource.getAttributeValue("name"));
                }
            }
        }
        for (DataOutputAssociation dataOutputAssociation : dataOutputAssociations) {
            ItemAwareElement iae =dataOutputAssociation.getTarget();
            String targetRef = iae.getId();//getAttributeValue("targetRef");
            if(targetRef==null ){
                break;
            }
            ModelElementInstance tempTarget = modelInstance.getModelElementById(targetRef);
            String tempTtp = iae.getElementType().getTypeName();//tempTarget.getElementType();
            if (tempTtp.equals("dataObject")) {
                output.add(tempTarget.getAttributeValue("name"));
            }
        }
        if(outflag==1){
            return input;
        }else{
            return output;
        }

    }

    /**
     * 给每一个任务编号
     */
    public void insureState(){
//        Map<String,List<String>> processOrder = null;
        taskAndState=new HashMap<String,String>();
        if(processOrder==null){
            process();
        }
        int state=0;
        for(Map.Entry<String,List<String>>entry:processOrder.entrySet()){
            taskAndState.put(entry.getKey(),state+++"");
        }
    }

    /**
     * 根据任务编号,获得它对应的状态
     * @param taskId
     * @return
     */
    public String getStateByTaskId(String taskId){
        return taskAndState.get(taskId);
    }

}
