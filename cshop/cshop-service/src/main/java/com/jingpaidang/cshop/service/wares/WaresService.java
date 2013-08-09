package com.jingpaidang.cshop.service.wares;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jingpaidang.cshop.dao.wares.WaresMapper;
import com.jingpaidang.cshop.domain.ware.WareItem;
import com.jingpaidang.cshop.service.PlatformRpcService;

/**
 * 
 * @ClassName:	WaresService 
 * @Description:TODO(商品业务) 
 * @author:	Alex
 * @date:	2013-7-3 下午3:32:45 
 *
 */
@Service("wareService")
public class WaresService {
	
	private static final Logger logger = LoggerFactory.getLogger(WaresService.class);
	
	
	 @Resource
	 private PlatformRpcService rpcService;
	 @Resource
	 private WaresMapper waresMapper ;
	 
	 /**
	  * 
	  * @Title:downloadWares 
	  * @Description:TODO(下载商品) 
	  * @param:@param accountId 
	  * @return:void 
	  * @throws 
	  * @Create: 2013-7-3 下午4:07:06
	  * @Author : Alex
	  */
	public int downloadWares(int accountId) throws IOException, ApiException {
		List<WareItem> list = rpcService.findAllWareItems(accountId);
		
		int count = 0 ;
		if(list==null || list.size()<=0){
			return count ;
		}
		for(WareItem waresItem:list){
			Map<String,Object> paras = new HashMap<String,Object>();
			paras.put("itemId", waresItem.getItemId());
			paras.put("accountId", accountId);
			WareItem old = waresMapper.getWareItemByItemIdAndAccountId(paras);
			if(old!=null && old.getItemId().longValue()==waresItem.getItemId().longValue())continue ;//库中已存在的不再更新
			waresMapper.insert(waresItem);
			count+=1 ;
		}
		return count ;
	}
	public List<WareItem> getWareItemList(Map<String,Object> paras){
		List<WareItem> list = waresMapper.getWareItemList(paras);
		return list;
	}
}
