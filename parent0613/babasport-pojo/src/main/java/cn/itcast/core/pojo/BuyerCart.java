package cn.itcast.core.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 购物车
 * @author xujie
 *
 */
public class BuyerCart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//购物项结果集
	private List<BuyerItem> items = new ArrayList<BuyerItem>();

	//追加当前款
	public void addItem(BuyerItem item){
		//判断是否同款
		if(items.contains(item)){//底层走的是equals
			for (BuyerItem it : items) {
				if(it.equals(item)){
					it.setAmount(it.getAmount() + item.getAmount());
					break;
				}
			}
		}else{
			items.add(item);
		}
	}

	public List<BuyerItem> getItems() {
		return items;
	}

	public void setItems(List<BuyerItem> items) {
		this.items = items;
	}
	
	//小计
	//商品数量
	@JsonIgnore
	public Integer getProductAmount(){
		Integer result = 0;
		for (BuyerItem item : items) {
			result += item.getAmount();
		}
		return result;
	}
	//商品金额
	@JsonIgnore
	public Float getProductPrice(){
		Float result = 0f;
		for (BuyerItem item : items) {
			result += item.getAmount()*item.getSku().getPrice();
		}
		return result;
	}
	//运费
	@JsonIgnore
	public Float getFee(){
		Float result = 0f;
		if(getProductPrice() <= 99f){
			result = 6f;
		}
		return result;
	}
	//总金额
	@JsonIgnore
	public Float getTotalPrice(){
		return getProductPrice() + getFee();
	}
}
