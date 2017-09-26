import React from 'react'
import { Flex } from 'antd-mobile'
import './addCard.less'
import NavBar from '../../components/NavBar'
import gold_card from '../../img/gold_card.png'
import wgold_card from '../../img/wgold_card.png'
import diamonds_card from '../../img/diamonds_card.png'
import extreme_card from '../../img/extreme_card.png'
import times_card from '../../img/times_card.png'
import { getCardList } from '../../services/service.js'
import { payment, getWXConfig, membershipCard } from '../../services/pay.js'
import PropTypes from 'prop-types';


class AddCard extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            card: [
                { name: '次卡', src: times_card },
                { name: '金卡', src: gold_card },
                { name: '白金卡', src: wgold_card },
                { name: '钻石卡', src: diamonds_card },
                { name: '至尊卡', src: extreme_card }
            ],
            arrowIndex: 0,
            arrowName: '次卡',
            services: [],
            serviceId: -1,//选中的serviceId
            totalPrice:0,//选中的卡的totalprice
        }
    }
    componentDidMount() {
        //通过后台对微信签名的验证。
        getWXConfig({
            targetUrl: window.location.href,
        }).then((res) => {
            let data = res.data;
            //先注入配置JSSDK信息
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appId, // 必填，公众号的唯一标识
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.nonceStr, // 必填，生成签名的随机串
                signature: data.signature,// 必填，签名，见附录1
                jsApiList: [
                    'checkJsApi',
                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
            wx.ready(function () {
                console.log("验证微信接口成功");
            });

        }).catch((error) => { console.log(error) });

        let mySwiper3 = new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: false,
            slidesPerView: 4,
            slidesPerGroup: 1,
            centeredSlides: false,
            prevButton: '.swiper-button-prev',
            nextButton: '.swiper-button-next'
            // 如果需要分页器
        });

        getCardList({
            page: 1,
            number: 22
        }).then((res) => {
            //console.log(res);
            if (res.data.code == '0') {
                //初始化得到次卡的id 因为页面上次卡是第一个
                //如果不存在 id = -1

                let serviceId = -1;
                let totalPrice = 0;
                let cards = res.data.data;
                for (let card of cards) {
                    if (card.name == '次卡') {
                        serviceId = card.id;
                        totalPrice = card.price;
                        break;
                    }
                }

                this.setState({
                    services: cards,
                    serviceId: serviceId,
                    totalPrice:totalPrice
                })

            }
        }).catch((error) => { console.log(error) });



    }

    onHanleArrowDisplay = (name, index) => {

        let serviceId = -1;
        let totalPrice = 0;
        let cards = this.state.services;
        for (let card of cards) {
            if (card.name == name) {
                serviceId = card.id;
                totalPrice = card.price;
                break;
            }
        }

        this.setState({
            arrowIndex: index,
            arrowName: name,
            serviceId: serviceId,
            totalPrice:totalPrice
        });



    }


    handlePay = () => {
        let state = this.checkPayState();
        if (!state) {
            alert("不能发起支付");
        }

        if (state) {
            membershipCard({//传递所需的参数
                //"openId": 'oBaSqs4THtZ-QRs1IQk-b8YKxH28',
                "openId": window.localStorage.getItem('openid'),
                "serviceId": this.state.serviceId,
                "totalPrice": this.state.totalPrice,
            }).then((res) => {
                if (res.data.code == 0) {
                    let data = res.data.data;
                    console.log(data);
                    this.onBridgeReady(data.appId, data.timeStamp,
                        data.nonceStr, data.package,
                        data.signType, data.paySign);
                } else {
                    alert('支付失败');
                }

            }).catch((error) => { console.log(error) });
        }

    }


    checkPayState = () => {
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
            return true;
        } else {
            return true;
        }
    }


    onBridgeReady = (appId, timeStamp, nonceStr, prepay_id, signType,
        paySign, type) => {
        WeixinJSBridge.invoke('getBrandWCPayRequest', {
            "appId": appId, // 公众号名称，由商户传入
            "timeStamp": timeStamp, // 时间戳，自1970年以来的秒数
            "nonceStr": nonceStr, // 随机串
            "package": prepay_id,
            "signType": signType, // 微信签名方式：
            "paySign": paySign
            // 微信签名
        }, function (res) {
            console.log("支付结果:");
            console.log(res);
            if (res.err_msg == "get_brand_wcpay_request:ok") {
                this.context.router.history.push('/result')
            }
        });
    }

    render() {

        let cards = this.state.card;

        let cardList = cards.map((item, index) => {
            return <div key={index} className="card-item swiper-slide" onClick={() => { this.onHanleArrowDisplay(item.name, index) }}>
                <img src={item.src} />
                <div className='label'>{item.name}</div>
                <div className={`arrow ${this.state.arrowIndex == index ? '' : 'hidden'}`}></div>
            </div>

        });

        let serviceItem = this.state.services.map((item, index) => {
            let service = item
            if (item.name == this.state.arrowName) {

                let proInfos = service.projectInfos;
                let item = proInfos.map((item1, index1) => {
                    return <div key={index + '' + index1} className='vip-service-item'>
                        <div className='label-left'>{item1.project.name}</div>
                        <div className='label-right'>
                            <span className='count'>{item1.times}</span>
                            <span className='unit'>次</span>
                        </div>
                    </div>
                });
                return item;
            }
        });

        return (<div className="body-bac">
            <NavBar title={'会员卡添加'}></NavBar>
            <div className="swiper-container all-card-list" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    {cardList}
                </div>
                <div className="swiper-button-prev swiper-button-white"></div>
                <div className="swiper-button-next swiper-button-white"></div>
            </div>

            <div className='vip-service'>
                <div className='vip-service-div'>
                    <div className='vip-service-header'>会员专项</div>
                    {serviceItem}
                </div>
            </div>


            <div className='bottom-pay-button'>
                <Flex style={{ height: '100%' }}>
                    <Flex.Item className='lable'>合计:</Flex.Item>
                    <Flex.Item style={{ color: 'red' }}><span style={{ fontSize: '12px' }}>￥</span><span style={{ fontSize: '16px' }}>{this.state.totalPrice}</span></Flex.Item>
                    <div className='pay-button'>
                        <Flex style={{ height: '100%' }}>
                            <Flex.Item style={{ textAlign: 'center', color: '#fff' }} onClick={() => { this.handlePay() }}>立即支付</Flex.Item>
                        </Flex>
                    </div>
                </Flex>
            </div>

        </div>
        );
    }
}

export default AddCard
AddCard.contextTypes = {
    router: PropTypes.object.isRequired
}
