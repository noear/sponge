package org.noear.sponge.admin.model.sponge;

import org.noear.wood.*;

import java.util.*;

/// <summary>
/// 生成:2018/08/06 05:44:04
/// 
/// </summary>
public class DoveSmsModel implements IBinder {
    public long sms_id;
    public int agroup_id;
    public int template_id;
    public String content;
    public String channels;
    public String var_setting;
    public String mobile;
    public int model_id;
    public String model_name;
    public String model_name_disp;
    public int rule_id;
    public String rule_name;
    public String rule_name_disp;
    public Date target_time;
    public int status;
    public String operator;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        sms_id = s.get("sms_id").value(0L);
        agroup_id = s.get("agroup_id").value(0);
        template_id = s.get("template_id").value(0);
        content = s.get("content").value(null);
        channels = s.get("channels").value(null);
        var_setting = s.get("var_setting").value(null);
        mobile = s.get("mobile").value(null);
        model_id = s.get("model_id").value(0);
        model_name = s.get("model_name").value(null);
        model_name_disp = s.get("model_name_disp").value(null);
        rule_id = s.get("rule_id").value(0);
        rule_name = s.get("rule_name").value(null);
        rule_name_disp = s.get("rule_name_disp").value(null);
        target_time = s.get("target_time").value(null);
        status = s.get("status").value(0);
        operator = s.get("operator").value(null);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
    }

    public IBinder clone() {
        return new DoveSmsModel();
    }

    public long getSms_id() {
        return sms_id;
    }

    public void setSms_id(long sms_id) {
        this.sms_id = sms_id;
    }

    public int getAgroup_id() {
        return agroup_id;
    }

    public void setAgroup_id(int agroup_id) {
        this.agroup_id = agroup_id;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getVar_setting() {
        return var_setting;
    }

    public void setVar_setting(String var_setting) {
        this.var_setting = var_setting;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getModel_name_disp() {
        return model_name_disp;
    }

    public void setModel_name_disp(String model_name_disp) {
        this.model_name_disp = model_name_disp;
    }

    public int getRule_id() {
        return rule_id;
    }

    public void setRule_id(int rule_id) {
        this.rule_id = rule_id;
    }

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getRule_name_disp() {
        return rule_name_disp;
    }

    public void setRule_name_disp(String rule_name_disp) {
        this.rule_name_disp = rule_name_disp;
    }

    public Date getTarget_time() {
        return target_time;
    }

    public void setTarget_time(Date target_time) {
        this.target_time = target_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getCreate_date() {
        return create_date;
    }

    public void setCreate_date(int create_date) {
        this.create_date = create_date;
    }

    public Date getCreate_fulltime() {
        return create_fulltime;
    }

    public void setCreate_fulltime(Date create_fulltime) {
        this.create_fulltime = create_fulltime;
    }

    public int getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(int update_date) {
        this.update_date = update_date;
    }

    public Date getUpdate_fulltime() {
        return update_fulltime;
    }

    public void setUpdate_fulltime(Date update_fulltime) {
        this.update_fulltime = update_fulltime;
    }
}