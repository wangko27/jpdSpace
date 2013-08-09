package com.jingpaidang.crm.service.order;

import com.jingpaidang.crm.domain.order.MemberOrder;
/**
 * 
 * @ClassName:	MemberOrderService 
 * @Description:TODO(会员订单业务接口) 
 * @author:	Alex
 * @date:	2013-8-8 上午11:48:07 
 *
 */
public interface MemberOrderService {
	/**
	 * 
	 * @Title:insert 
	 * @Description:TODO(添加会员订单) 
	 * @param:@param memberOrder
	 * @param:@return 
	 * @return:int 
	 * @throws 
	 * @Create: 2013-8-8 上午11:43:50
	 * @Author : Alex
	 */
	public long insert(MemberOrder memberOrder);
	/**
	 * 
	 * @Title:isOrderExist 
	 * @Description:TODO(根据订单Id判断是否已存在) 
	 * @param:@param orderId
	 * @param:@return 
	 * @return:boolean 
	 * @throws 
	 * @Create: 2013-8-8 上午11:44:52
	 * @Author : Alex
	 */
	public boolean isOrderExist(String orderId);
}
