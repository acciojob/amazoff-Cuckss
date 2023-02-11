package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
         private HashMap<String,Order>orderdb;
         private HashMap<String,DeliveryPartner>deliveryPartnerdb;
         private HashMap<DeliveryPartner, List<Order>>orderPartnerPairDb;
         private HashMap<String,String>orderTimeDb;

    public OrderRepository() {
        this.orderdb = new HashMap<String,Order>();
        this.deliveryPartnerdb=new HashMap<String,DeliveryPartner>();
        this.orderTimeDb=new HashMap<String,String>();
    }

    public String addOrder(Order order){
      orderdb.put(order.getId(),order);
      orderTimeDb.put(order.getId(),order.getTimeInString());
      return"success";
    }
  public String addPartner(String partnerId){
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        deliveryPartnerdb.put(partnerId,deliveryPartner);

        return "success";
  }
  public String addOrderPartnerPair(String orderId,String partnerId){
      Order order=orderdb.get(orderId);
      DeliveryPartner partner=deliveryPartnerdb.get(partnerId);
      List<Order>orders;
      if(!orderPartnerPairDb.containsKey(partner)){
         orders=new ArrayList<>();
          orders.add(order);
          orderPartnerPairDb.put(partner,orders);
      }
      else{
          orders=orderPartnerPairDb.get(partner);
          orders.add(order);
          orderPartnerPairDb.put(partner,orders);
      }
      partner.setNumberOfOrders(orders.size());
      return "Success";
  }
  public Order getOrderById(String orderId){
        return orderdb.get(orderId);
  }
  public DeliveryPartner  getPartnerById(String partnerId){
        return deliveryPartnerdb.get(partnerId);
  }
  public int  getOrderCountByPartnerId(String partnerId){
        DeliveryPartner deliveryPartner=deliveryPartnerdb.get(partnerId);
       int noOfOrders=orderPartnerPairDb.get(deliveryPartner).size();

       return noOfOrders;
  }
  public List<String> getOrdersByPartnerId(String partnerId){
        DeliveryPartner deliveryPartner=deliveryPartnerdb.get(partnerId);
        List<Order>listOrder=orderPartnerPairDb.get(deliveryPartner);
        List<String>ans=new ArrayList<>();
        for(Order order:listOrder){
            ans.add(order.getId());
        }
        return ans;
  }
  public List<String> getAllOrders(){
        List<String>allOrders=new ArrayList<>();
        for(String id : orderdb.keySet()){
            allOrders.add(id);
        }
        return allOrders;
  }
  public int getCountOfUnassignedOrders(){
        List<String> totalAssignedOrders=new ArrayList<>();
        int count=0;
        for(DeliveryPartner partner: orderPartnerPairDb.keySet()){
            List<Order>list=orderPartnerPairDb.get(partner);
            for(Order order:list){
                //here we are storing  the values in string
                totalAssignedOrders.add(order.getId());
            }
        }
        for(String id:orderdb.keySet()){
            if(!totalAssignedOrders.contains(id)){
                count++;
            }
        }
        return count;
  }
  public int getOrdersLeftAfterGivenTimeByPartnerId(String deliveryTime,String partnerId){
        int count=0;
      String arr[]=deliveryTime.split(":");
      int HH=Integer.parseInt(arr[0]);
      int MM=Integer.parseInt(arr[1]);
      int timeInInteger=HH*60+MM;
      DeliveryPartner deliveryPartner=deliveryPartnerdb.get(partnerId);
      List<Order>listOrder=orderPartnerPairDb.get(deliveryPartner);
      for(Order order: listOrder){
          if(order.getDeliveryTime()>timeInInteger){
              count++;
          }
      }
      return count;
  }
  public String getLastDeliveryTimeByPartnerId(String partnerId){
        int recent=Integer.MIN_VALUE;
      DeliveryPartner deliveryPartner=deliveryPartnerdb.get(partnerId);
      List<Order>listOrder=orderPartnerPairDb.get(deliveryPartner);
      Order theLastOrder=null;
      for(Order order:listOrder){
          //here we are storing  the values in string
        if(order.getDeliveryTime()>recent){
            recent=order.getDeliveryTime();
            theLastOrder=order;
        }
      }
      return orderTimeDb.get(theLastOrder.getTimeInString());
  }
  public String deletePartnerById(String partnerId){
      DeliveryPartner deliveryPartner=deliveryPartnerdb.get(partnerId);
      List<Order>listOrder=orderPartnerPairDb.get(deliveryPartner);
      orderPartnerPairDb.remove(deliveryPartner);
      deliveryPartnerdb.remove(partnerId);
      return "success";
      }

      public String deleteOrderById(String orderId){
     Order order=orderdb.get(orderId);
     orderdb.remove(orderId);
     for(DeliveryPartner deliveryPartner:orderPartnerPairDb.keySet()){
         if(orderPartnerPairDb.get(deliveryPartner).contains(orderId));
         break;
     }
     return "succcess";
      }
  }

