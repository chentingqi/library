package org.devops

def editUatGateway(service,ip){
    //活动中心
    if (service == 'activitycenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ac.url 172.31.129.84 8330 activitycenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ac.url 172.31.129.79 8330 activitycenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ac.url 172.31.129.79 172.31.129.84 8330 activitycenter"""}
    }
    //基础数据
    if (service == 'basedata'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.84 8300 basedata"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.79 8300 basedata"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.79 172.31.129.84 8300 basedata"""}
    }
    //第三方适配器
    if (service == 'thirdpartyadapter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ta.url 172.31.129.84 8080 thirdpartyadapter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ta.url 172.31.129.79 8080 thirdpartyadapter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ta.url 172.31.129.79 172.31.129.84 8080 thirdpartyadapter"""}
    }
    //用户中心
    if (service == 'usercenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh uc.url 172.31.129.84 8000 usercenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh uc.url 172.31.129.79 8000 usercenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh uc.url 172.31.129.79 172.31.129.84 8000 usercenter"""}
    }
    //支付中心
    if (service == 'paycenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh pc.url 172.31.129.84 8120 paycenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh pc.url 172.31.129.79 8120 paycenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh pc.url 172.31.129.79 172.31.129.84 8120 paycenter"""}
    }
    //内容管理
    if (service == 'sitecms'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cm.url 172.31.129.84 8040 sitecms"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cm.url 172.31.129.79 8040 sitecms"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cm.url 172.31.129.79 172.31.129.84 8040 sitecms"""}
    }
    //网站联盟
    if (service == 'siteleague'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh sl.url 172.31.129.84 8320 siteleague"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh sl.url 172.31.129.79 8320 siteleague"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh sl.url 172.31.129.79 172.31.129.84 8320 siteleague"""}
    }
    //题库中心
    if (service == 'questionbank'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh qb.url 172.31.129.81 8310 questionbank"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh qb.url 172.31.129.89 8310 questionbank"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh qb.url 172.31.129.81 172.31.129.89 8310 questionbank"""}
    }
    //消息中心
    if (service == 'messagecenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh mc.url 172.31.129.84 8090 messagecenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh mc.url 172.31.129.79 8090 messagecenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh mc.url 172.31.129.79 172.31.129.84 8090 messagecenter"""}
    }
    //课程中心
    if (service == 'coursecenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cc.url 172.31.129.81 8340 coursecenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cc.url 172.31.129.89 8340 coursecenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cc.url 172.31.129.89 172.31.129.81 8340 coursecenter"""}
    }
    //文件服务
    if (service == 'fileservice'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fs.url 172.31.129.105 8030 fileservice"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fs.url 172.31.129.105 8030 fileservice"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fs.url 172.31.129.105 8030 fileservice"""}
    }
    //物流中心
    if (service == 'logisticscenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh lg.url 172.31.129.146 8410 logisticscenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh lg.url 172.31.129.147 8410 logisticscenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh lg.url 172.31.129.146 172.31.129.147 8410 logisticscenter"""}
    }
    //开放资源中心
    if (service == 'opensource'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh os.url 172.31.129.81 8390 opensource"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh os.url 172.31.129.81 8390 opensource"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh os.url 172.31.129.81 172.31.129.81 8390 opensource"""}
    }
    //订单中心
    if (service == 'api'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh oc.url serviceuat.zywinner.com api"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh oc.url 172.31.129.79 8120 api"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh oc.url 172.31.129.79 172.31.129.84 8120 api"""}
    }
    //订单中心.CRM
    if (service == 'api'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh crm.old.url 172.31.103.149 8111 api"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh crm.old.url 172.31.103.149 8111 api"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh crm.old.url 172.31.103.149 8111 api"""}
    }
    //订单中心.学习
    if (service == 'api'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh st.old.oc.url 172.31.103.169 8065 api"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh st.old.oc.url 172.31.103.169 8065 api"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh st.old.oc.url 172.31.103.169 8065 api"""}
    }
    //订单中心.老网站
    if (service == 'api'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh oc.old.url 172.31.129.86 8089 api"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh oc.old.url 172.31.129.86 8089 api"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh oc.old.url 172.31.129.86 8089 api"""}
    }
    //学员服务
    if (service == 'studentservice'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ss.url 172.31.129.146 8370 studentservice"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ss.url 172.31.129.147 8370 studentservice"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ss.url 172.31.129.146 172.31.129.147 8370 studentservice"""}
    }
    //资金系统
    if (service == 'fundsystem'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fd.url 172.31.129.84 8070 fundsystem"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fd.url 172.31.129.84 8070 fundsystem"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fd.url 172.31.129.79 172.31.129.84 8070 fundsystem"""}
    }
    //人才服务
    if (service == 'talentbank'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh tb.url 172.31.129.81 8440 talentbank"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh tb.url 172.31.129.89 8440 talentbank"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh tb.url 172.31.129.89 172.31.129.81 8440 talentbank"""}
    }
    //企业服务
    if (service == 'corpservice'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cs.url 172.31.129.81 8430 corpservice"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cs.url 172.31.129.89 8430 corpservice"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh cs.url 172.31.129.89 172.31.129.81 8430 corpservice"""}
    }
    //资料中心
    if (service == 'doccenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh dc.url 172.31.129.81 8400 doccenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh dc.url 172.31.129.89 8400 doccenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh dc.url 172.31.129.89 172.31.129.81 8400 doccenter"""}
    }
    //卡券服务
    if (service == 'cardticket'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ck.url 172.31.129.81 8500 cardticket"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ck.url 172.31.129.89 8500 cardticket"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ck.url 172.31.129.89 172.31.129.81 8500 cardticket"""}
    }
    //推广系统
    if (service == 'extensionsystem'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh es.url 172.31.129.84 8350 extensionsystem"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh es.url 172.31.129.84 8350 extensionsystem"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh es.url 172.31.129.79 172.31.129.84 8350 extensionsystem"""}
    }
    //推广系统
    if (service == 'extensionsystem'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh es.new.url 172.31.129.158 8450 extensionsystem"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh es.new.url 172.31.129.158 8450 extensionsystem"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh es.new.url 172.31.129.158 8450 extensionsystem"""}
    }
    //直播中心
    if (service == 'livecenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh lc.url 172.31.129.146 8510 livecenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh lc.url 172.31.129.147 8510 livecenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh lc.url 172.31.129.146 172.31.129.147 8510 livecenter"""}
    }
    //流量订单
    if (service == 'floworder'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fo.url 172.31.129.146 8520 floworder"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fo.url 172.31.129.147 8520 floworder"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh fo.url 172.31.129.146 172.31.129.147 8520 floworder"""}
    }
    //商品中心
    if (service == 'goodscenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh gc.url 172.31.129.81 8490 goodscenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh gc.url 172.31.129.89 8490 goodscenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh gc.url 172.31.129.81 172.31.129.89 8490 goodscenter"""}
    }
    //综合小程序
    if (service == 'unifyapplet'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ua.url 172.31.129.81 8530 unifyapplet"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ua.url 172.31.129.89 8530 unifyapplet"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ua.url 172.31.129.89 172.31.129.81 8530 unifyapplet"""}
    }
    //外部数据
    if (service == 'externaldata'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ex.url 172.31.129.84 8100 externaldata"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ex.url 172.31.129.79 8100 externaldata"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ex.url 172.31.129.84 172.31.129.79 8100 externaldata"""}
    }
    

}
