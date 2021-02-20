package org.devops


def editProdGateway(service,ip){
// 基础服务上的

    //活动中心
    if (service == 'activitycenter'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ac.url 172.31.3.83 8330 activitycenter"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ac.url 172.31.3.124 8330 activitycenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ac.url 172.31.3.83 172.31.3.124 8330 activitycenter"""}
    }
    //推广系统
    if (service == 'extensionsystem'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.url 172.31.3.83 8350 extensionsystem"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.url 172.31.3.124 8350 extensionsystem"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.url 172.31.3.124 172.31.3.83 8350 extensionsystem"""}
    }
    
    //基础数据
    if (service == 'basedata'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh bd.url 172.31.3.83 8300 basedata"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh bd.url 172.31.3.124 8300 basedata"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh bd.url 172.31.3.83 172.31.3.124 8300 basedata"""}
    }
    //第三方适配器
    if (service == 'thirdpartyadapter'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ta.url 172.31.3.83 8080 thirdpartyadapter"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ta.url 172.31.3.124 8080 thirdpartyadapter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ta.url 172.31.3.83 172.31.3.124 8080 thirdpartyadapter"""}
    }
    //用户中心
    if (service == 'usercenter'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh uc.url 172.31.3.83 8000 usercenter"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh uc.url 172.31.3.124 8000 usercenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh uc.url 172.31.3.83 172.31.3.124 8000 usercenter"""}
    }
    //支付中心
    if (service == 'paycenter'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh pc.url 172.31.3.83 8120 paycenter"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh pc.url 172.31.3.124 8120 paycenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh pc.url 172.31.3.83 172.31.3.124 8120 paycenter"""}
    }
    //内容管理
    if (service == 'sitecms'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cm.url 172.31.3.83 8040 sitecms"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cm.url 172.31.3.124 8040 sitecms"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cm.url 172.31.3.83 172.31.3.124 8040 sitecms"""}
    }
    
    //资金系统
    if (service == 'fundsystem'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fd.url 172.31.3.83 8070 fundsystem"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fd.url 172.31.3.124 8070 fundsystem"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fd.url 172.31.3.83 172.31.3.124 8070 fundsystem"""}
    }
    //网站联盟
    if (service == 'siteleague'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh sl.url 172.31.3.83 8320 siteleague"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh sl.url 172.31.3.124 8320 siteleague"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh sl.url 172.31.3.83 172.31.3.124 8320 siteleague"""}
    }
    //外部数据
    if (service == 'externaldata'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ex.url 172.31.3.83 8100 externaldata"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ex.url 172.31.3.124 8100 externaldata"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ex.url 172.31.3.83 172.31.3.124 8100 externaldata"""}
    }
    
    //消息中心
    if (service == 'messagecenter'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh mc.url 172.31.3.83 8090 messagecenter"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh mc.url 172.31.3.124 8090 messagecenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh mc.url 172.31.3.83 172.31.3.124 8090 messagecenter"""}
    }
    
    //日志中心
    if (service == 'logcenter'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh logc.url 172.31.3.83 8140 logcenter"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh logc.url 172.31.3.124 8140 logcenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh logc.url 172.31.3.83 172.31.3.124 8140 logcenter"""}
    }
    
    //验证码服务
    if (service == 'captchaservice'){
       if (ip == '172.31.3.83'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh mc.url 172.31.3.83 8150 captchaservice"""}
       if (ip == '172.31.3.124'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh mc.url 172.31.3.124 8150 captchaservice"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh mc.url 172.31.3.83 172.31.3.124 8150 captchaservice"""}
    }
    
        
    
    
    //内容服务上的
    //题库中心
    if (service == 'questionbank'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh qb.url 172.31.3.125 8310 questionbank"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh qb.url 172.31.3.122 8310 questionbank"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh qb.url 172.31.3.125 172.31.3.122 8310 questionbank"""}
    }

    //课程中心
    if (service == 'coursecenter'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cc.url 172.31.3.125 8340 coursecenter"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cc.url 172.31.3.122 8340 coursecenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cc.url 172.31.3.122 172.31.3.125 8340 coursecenter"""}
    }

    //开放资源中心
    if (service == 'opensource'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh os.url 172.31.3.125 8390 opensource"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh os.url 172.31.3.122 8390 opensource"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh os.url 172.31.3.122 172.31.3.125 8390 opensource"""}
    }

    //人才服务
    if (service == 'talentbank'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh tb.url 172.31.3.125 8440 talentbank"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh tb.url 172.31.3.122 8440 talentbank"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh tb.url 172.31.3.122 172.31.3.125 8440 talentbank"""}
    }
    //源点库
    if (service == 'cknowledgebase'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cb.url 172.31.3.125 8550 cknowledgebase"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cb.url 172.31.3.122 8550 cknowledgebase"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cb.url 172.31.3.122 172.31.3.125 8550 cknowledgebase"""}
    }
    //企业服务
    if (service == 'corpservice'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cs.url 172.31.3.125 8430 corpservice"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cs.url 172.31.3.122 8430 corpservice"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cs.url 172.31.3.122 172.31.3.125 8430 corpservice"""}
    }
    //资料中心
    if (service == 'doccenter'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh dc.url 172.31.3.125 8400 doccenter"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh dc.url 172.31.3.122 8400 doccenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh dc.url 172.31.3.122 172.31.3.125 8400 doccenter"""}
    }
    //卡券服务
    if (service == 'cardticket'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ck.url 172.31.3.125 8500 cardticket"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ck.url 172.31.3.122 8500 cardticket"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ck.url 172.31.3.122 172.31.3.125 8500 cardticket"""}
    }
    
    //商品中心
    if (service == 'goodscenter'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh gc.url 172.31.3.125 8490 goodscenter"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh gc.url 172.31.3.122 8490 goodscenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh gc.url 172.31.3.125 172.31.3.122 8490 goodscenter"""}
    }
    
    //综合小程序
    if (service == 'unifyapplet'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ua.url 172.31.3.125 8530 unifyapplet"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ua.url 172.31.3.122 8530 unifyapplet"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ua.url 172.31.3.122 172.31.3.125 8530 unifyapplet"""}
    }
    

    //文件服务
    if (service == 'fileservice'){
       if (ip == '172.31.3.127'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fs.url 172.31.3.127 8030 fileservice"""}
       if (ip == '172.31.3.174'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fs.url 172.31.3.174 8030 fileservice"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fs.url 172.31.3.127 172.31.3.174 8030 fileservice"""}
    }
    
    
//学员服务   
    //教务中心
    if (service == 'educationalcenter'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ec.url 172.31.4.1 8480 educationalcenter"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ec.url 172.31.4.2 8480 educationalcenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ec.url 172.31.4.1 172.31.4.2 8480 educationalcenter"""}
    }    
    
    //智能外呼
    if (service == 'intellectcallcenter'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ic.url 172.31.4.1 8110 intellectcallcenter"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ic.url 172.31.4.2 8110 intellectcallcenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ic.url 172.31.4.1 172.31.4.2 8110 intellectcallcenter"""}
    }
    
    //物流中心
    if (service == 'logisticscenter'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh lg.url 172.31.4.1 8410 logisticscenter"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh lg.url 172.31.4.2 8410 logisticscenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh lg.url 172.31.4.1 172.31.4.2 8410 logisticscenter"""}
    }
    
    //学员服务
    if (service == 'studentservice'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ss.url 172.31.4.1 8370 studentservice"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ss.url 172.31.4.2 8370 studentservice"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ss.url 172.31.4.1 172.31.4.2 8370 studentservice"""}
    }

    //直播中心
    if (service == 'livecenter'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh lc.url 172.31.4.1 8510 livecenter"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh lc.url 172.31.4.2 8510 livecenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh lc.url 172.31.4.1 172.31.4.2 8510 livecenter"""}
    }
    //流量订单
    if (service == 'floworder'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fo.url 172.31.4.1 8520 floworder"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fo.url 172.31.4.2 8520 floworder"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh fo.url 172.31.4.1 172.31.4.2 8520 floworder"""}
    }

    //仓储中心
    if (service == 'storagecenter'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh st.url 172.31.4.1 8470 storagecenter"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh st.url 172.31.4.2 8470 storagecenter"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh st.url 172.31.4.1 172.31.4.2 8470 storagecenter"""}
    }

    //业绩系统
    if (service == 'performancesystem'){
       if (ip == '172.31.4.1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh pf.url 172.31.4.1 8540 performancesystem"""}
       if (ip == '172.31.4.2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh pf.url 172.31.4.2 8540 performancesystem"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh pf.url 172.31.4.1 172.31.4.2 8540 performancesystem"""}
    }


    /*//推广系统admin
    if (service == 'extensioncenter-admin'){
       if (ip == '172.31.4.9'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.admin.url 172.31.4.9 8450 extensioncenter-admin"""}
       if (ip == '172.31.4.10'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.admin.url 172.31.4.10 8450 extensioncenter-admin"""}
      if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.admin.url 172.31.4.9 172.31.4.10 8450 extensioncenter-admin"""}
    }
    //推广系统behavior
    if (service == 'extensioncenter-behavior'){
       if (ip == '172.31.4.9'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.behavior.url 172.31.4.9 8460 extensioncenter-behavior"""}
       if (ip == '172.31.4.10'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.behavior.url 172.31.4.10 8460 extensioncenter-behavior"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.behavior.url 172.31.4.9 172.31.4.10 8460 extensioncenter-behavior"""}
    }
    //推广系统push
    /*if (service == 'extensioncenter-push'){
       if (ip == '172.31.4.9'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.push.url 172.31.4.9 8560 extensioncenter-push"""}
       if (ip == '172.31.4.10'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.push.url 172.31.4.10 8560 extensioncenter-push"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.push.url 172.31.4.9 172.31.4.10 8560 extensioncenter-push"""}
    }
    //推广系统receive
    if (service == 'extensioncenter-receive'){
       if (ip == '172.31.4.9'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.receive.url 172.31.4.9 8570 extensioncenter-receive"""}
       if (ip == '172.31.4.10'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.receive.url 172.31.4.10 8570 extensioncenter-receive"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.receive.url 172.31.4.9 172.31.4.10 8570 extensioncenter-receive"""}
    }
    //推广系统transfer
    if (service == 'extensioncenter-transfer'){
       if (ip == '172.31.4.9'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.transfer.url 172.31.4.9 8580 extensioncenter-transfer"""}
       if (ip == '172.31.4.10'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.transfer.url 172.31.4.10 8580 extensioncenter-transfer"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh es.transfer.url 172.31.4.9 172.31.4.10 8580 extensioncenter-transfer"""}
    }*/



    //老订单中心
    if (service == 'api'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh oc.old.url 172.31.3.214 8000 api"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh oc.old.url 172.31.3.244 8000 api"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh oc.old.url 172.31.3.214 172.31.3.244 8000 api"""}
    }
    //订单中心.CRM
    //if (service == 'api'){
    //   if (ip == '10.31.44.79'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh crm.old.url 10.31.44.79 80 api"""}
    //   if (ip == '10.31.44.79'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh crm.old.url 10.31.44.79 80 api"""}
    //   if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh crm.old.url 10.31.44.79 80 api"""}
    //}
    if (service == 'contentplatform'){
       if (ip == '172.31.3.125'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cp.url 172.31.3.125 8170 contentplatform"""}
       if (ip == '172.31.3.122'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cp.url 172.31.3.122 8170 contentplatform"""}
       if (ip == 'all'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh cp.url 172.31.3.122 172.31.3.125 8170 contentplatform"""}
    }

}
