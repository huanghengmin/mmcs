package com.hzih.mmcs.domain;

/**
 * ID，平台最终
 * 
 * @author www
 * @hibernate.class table="district"
 */
public class InitSystem {
	
	/**
	 * @hibernate.id column="id" generator-class="increment" type="int"
	 *               length="10"
	 */
	Long id;
	
	/**
	 * 地区编码
	 * 
	 * @hibernate.property column="root_code" type="string" length="20"
	 */
	String rootCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRootCode() {
        return rootCode;
    }

    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
    }
}
