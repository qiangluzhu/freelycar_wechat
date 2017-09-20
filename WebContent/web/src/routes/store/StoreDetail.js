import React from 'react';
import { Flex, Tabs } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import Star from '../../components/Star'
import './CooperativeStore.less'
import '../autoInsurance/Inquiry.less'
import { storeDetail, listComment } from '../../services/store.js'
import { getWXConfig } from '../../services/pay.js'
const TabPane = Tabs.TabPane
class CooperativeStore extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            name: '',
            address: '',
            phone: '',
            star: '',
            closingTime: '',
            openingTime: '',
            fix: [],
            beauty: [],
            storefavours: [],
            comments: [],
            imgs: [],
            latitude: 0, // 纬度，浮点数，范围为90 ~ -90
            longitude: 0, // 经度，浮点数，范围为180 ~ -180。
        }
    }

    componentDidMount() {


        //通过后台对微信签名的验证。
        //这块的jsapi主要针对地图位置信息

        getWXConfig({ "targetUrl": window.location.href }).then((res) => {
            //if (res.data.code == '0') 
                let data = res.data;
                //先注入配置JSSDK信息
                wx.config({
                    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: data.appId, // 必填，公众号的唯一标识
                    timestamp: data.timestamp, // 必填，生成签名的时间戳
                    nonceStr: data.nonceStr, // 必填，生成签名的随机串
                    signature: data.signature,// 必填，签名，见附录1
                    jsApiList: ["getLocation", "openLocation"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });

                wx.ready(function () {
                    console.log("验证微信接口成功");
                });


        }).catch((error) => {
            console.log(error)
        })





        storeDetail({ storeId: this.props.match.params.storeId }).then((res) => {
            console.log(res)
            if (res.data.code == '0') {
                let store = res.data.data.store
                this.setState({
                    name: store.name,
                    address: store.address,
                    imgs: store.imgUrls,
                    phone: store.phone,
                    star: res.data.data.star,
                    closingTime: store.closingTime,
                    openingTime: store.openingTime,
                    beauty: res.data.data.beauty,
                    fix: res.data.data.fix,
                    storefavours: store.storefavours,
                    latitude: store.latitude, // 纬度，浮点数，范围为90 ~ -90
                    longitude: store.longitude, // 经度，浮点数，范围为180 ~ -180。
                })
                let mySwiper2 = new Swiper(this.swiperID2, {
                    direction: 'horizontal',
                    loop: false,
                    slidesPerView: 1.2,
                    setWrapperSize: true,
                    spaceBetween: 10,
                    slidesOffsetBefore: 11,
                    slidesOffsetAfter: 10,
                })



                let mySwiper1 = new Swiper(this.swiperID, {
                    direction: 'horizontal',
                    loop: true,
                    // 如果需要分页器
                    pagination: '.swiper-pagination',
                });

            }
        }).catch((error) => {
            console.log(error)
        })

        listComment({
            storeId: '1'
        }).then((res) => {
            //console.log(res)
            if (res.data.code == '0') {
                let comments = []
                for (let item of res.data.data) {
                    let comment = {
                        commentDate: item.commentDate,
                        phone: item.phone.substring(0, 3) + '****' + item.phone.substring(7),
                        star: item.stars,
                        comment: item.comment
                    }
                    comments.push(comment)
                }
                this.setState({
                    comments: comments
                })
            }
        }).catch((error) => { console.log(error) })
    }




    //打开微信内置地图
    openWXMap = () => {
        wx.openLocation({
            //latitude: 31.228201, // 纬度，浮点数，范围为90 ~ -90
            //longitude: 121.454897, // 经度，浮点数，范围为180 ~ -180。
            latitude: this.state.latitude, // 纬度，浮点数，范围为90 ~ -90
            longitude: this.state.longitude, // 经度，浮点数，范围为180 ~ -180。
            name: this.state.name, // 位置名
            address: this.state.address, // 地址详情说明
            scale: 20, // 地图缩放级别,整形值,范围从1~28。默认为最大
            infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
        });

    }



    render() {
        let sf = this.state.storefavours;
        let imgs = this.state.imgs.map((item, index) => {
            return <div key={index} className="swiper-slide  banner-img"><img src={`http://www.freelycar.com/store/${item.url}`} alt="" /></div>
        })
        let couponList = sf.map((item, index) => {
            return <div key={index} className="swiper-slide cooperative-store-coupon">
                <Flex className="coupon" direction="column" align="start">
                    <Flex style={{ height: '1.3rem', background: '#fff', width: '100%' }}>
                        <Flex className="money" direction="column" align="end">
                            <div style={{ fontSize: '.48rem' }}><span style={{ fontSize: '.24rem' }}>￥</span>{item.favour.set.buyPrice}</div>
                            <div style={{ color: '#8c8c8c', fontSize: '.22rem', marginTop: '.05rem' }} className="money-buyprice">
                                <span style={{ fontSize: '.24rem' }}>￥</span>{item.favour.set.presentPrice}
                                <i>
                                </i>
                            </div>
                        </Flex>
                        <div className="parting-line"></div>
                        <Flex style={{ flex: 'auto' }}>
                            <Flex direction="column" align="start">
                                <div style={{ fontSize: '.32rem', marginLeft: '.2rem', lineHeight: '.4rem' }}>{item.favour.name}</div>
                                <div style={{ fontSize: '.24rem', lineHeight: '.4rem', marginLeft: '.2rem' }}>{item.favour.content}</div>
                            </Flex>
                            <Flex className="use-button">
                                <div className="use-button-plus">-</div>
                                <div className="number">1</div>
                                <div className="use-button-add" onClick={() => { this.setState() }}>+</div>
                            </Flex>
                        </Flex>
                    </Flex>
                    <div className="coupon-info">
                        <span className="phone">限客户手机号：{window.localStorage.getItem('phone')}</span>
                        <span className="time">截止日期：{item.favour.buyDeadline}</span>
                    </div>
                </Flex>
            </div>
        }), fixList = this.state.fix.map((item, index) => {
            return <Flex index={index} style={{ width: '100%', borderBottom: '1px solid #dfdfe1' }} >
                <div >
                    <Flex direction="column" align="start" justify="center" style={{ height: '1.3rem' }}>
                        <div className="beauty-title">{item.name}</div>
                        <div className="beauty-aim">{item.comment}</div>
                    </Flex>
                </div>
                <div className="money">
                    <span style={{ fontSize: '.24rem' }}>￥</span>{item.price + item.pricePerUnit * item.referWorkTime}
                </div>
            </Flex>
        }), beautyList = this.state.beauty.map((item, index) => {
            return <Flex index={index} style={{ width: '100%', borderBottom: '1px solid #dfdfe1' }} >
                <div>
                    <Flex direction="column" align="start" justify="center" style={{ height: '1.3rem' }}>
                        <div className="beauty-title">{item.name}</div>
                        <div className="beauty-aim">{item.comment}</div>
                    </Flex>
                </div>
                <div className="money">
                    <span style={{ fontSize: '.24rem' }}>￥</span>{item.price + item.pricePerUnit * item.referWorkTime}
                </div>
            </Flex>
        }), commentList = this.state.comments.map((item, index) => {
            return <Flex className="comment" align="start" key={index}>
                <div className="avatar"><img /></div>
                <Flex.Item>
                    <div style={{ width: '100%' }}><span className="phone">{item.phone}</span><span className="time">{item.commentDate}</span></div>
                    <Star number={item.star}> </Star>
                    <div className="introduction">{item.comment} </div>
                </Flex.Item>
            </Flex>
        })
        return <div className="store-detail body-bac">
            <NavBar title="门店详情" />
            <div className="swiper-container" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    {imgs}
                </div>
                <div className="swiper-pagination circle-color"></div>
            </div>
            <Flex className="cooperative-store-list" style={{ paddingLeft: '.42rem' }}>

                <Flex direction="column" align="start" justify="between" >
                    <div className="store-name">
                        {this.state.name}
                        {/* <span style={{ fontSize: '.18rem', color: '#e42f2f', marginLeft: '.04rem' }}>{this.state.star}分</span> */}
                    </div>
                    <Flex className="info-font time">
                        <div className="time-icon"></div>
                        营业时间：{this.state.openingTime}-{this.state.closingTime}
                    </Flex>
                    <Flex className="time" align="end" >
                        <div>
                            <Flex className="address">
                                <div className="address-icon"></div>
                                <p className="info-font" style={{ color: '#636363', width: '5rem' }} onClick={() => { this.openWXMap() }} >{this.state.address}</p>

                            </Flex>
                            <div className="info-identify">
                                <span className="identification">免费安全监测</span>
                                <span className="identification">下雨保</span>
                            </div>
                        </div>
                    </Flex>
                </Flex>
                <Flex className="icon">
                    <div className="hr"></div>
                    <a href="tel:18512591863" style={{ display: 'inline-block' }}><div className="tel-icon" ></div></a>
                </Flex>
            </Flex>
            <Flex className="store-detail-title">
                <div className="sign"></div>
                <div className="title">优惠活动</div>
            </Flex>
            <div className="swiper-container" ref={self => this.swiperID2 = self}>
                <div className="swiper-wrapper ">
                    {couponList}
                </div>
            </div>
            <div style={{ height: '.21rem', background: '#fff', marginTop: '.04rem' }}>
            </div>
            <Tabs defaultActiveKey="1" swipeable underlineColor="#5a88e5" className="store-service">
                <TabPane tab='门店服务' key="1" >
                    <Tabs defaultActiveKey="1" swipeable underlineColor="#5a88e5" className="tabpane1" >
                        <TabPane tab='汽车美容' key="1" style={{ borderTop: '1px solid #dfdfe1' }} >
                            {beautyList}
                        </TabPane>
                        <TabPane tab='维修保养' key="2" style={{ borderTop: '1px solid #dfdfe1' }}>
                            {fixList}
                        </TabPane>
                    </Tabs>
                </TabPane>
                <TabPane tab='门店评价' key="2" className="tabpane2">
                    {commentList}
                </TabPane>
            </Tabs>
            {/* <div className='bottom-pay-button'>
                <Flex style={{ height: '100%' }}>
                    <Flex.Item className='lable'>合计:</Flex.Item>
                    <Flex.Item style={{ color: 'red' }}>￥999</Flex.Item>
                    <div className='pay-button'>
                        <Flex style={{ height: '100%' }}>
                            <Flex.Item style={{ textAlign: 'center', color: '#fff' }}>立即支付</Flex.Item>
                        </Flex>
                    </div>
                </Flex>
            </div> */}
        </div>
    }

}
export default CooperativeStore