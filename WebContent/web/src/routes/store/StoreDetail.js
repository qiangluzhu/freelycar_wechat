import React from 'react';
import { Flex, Tabs } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import Star from '../../components/Star'
import './CooperativeStore.less'

const TabPane = Tabs.TabPane
class CooperativeStore extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false
        }
    }

    componentDidMount() {
        let mySwiper = new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: true,
            // 如果需要分页器
            pagination: '.swiper-pagination',
        });
        let mySwiper2 = new Swiper(this.swiperID2, {
            direction: 'horizontal',
            loop: false,
            slidesPerView: 1.2,
            setWrapperSize: true,
            spaceBetween: 10,
            slidesOffsetBefore: 11,
            slidesOffsetAfter: 10,
        })
    }
    render() {
        return <div className="store-detail">
            <NavBar title="门店详情" />
            <div className="swiper-container" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    <div className="swiper-slide  banner-img ">Slide 1</div>
                    <div className="swiper-slide  banner-img">Slide 2</div>
                </div>
                <div className="swiper-pagination circle-color"></div>
            </div>
            <Flex className="cooperative-store-list">
                <Flex className="picture">
                    <img src="" alt="" />
                </Flex>
                <Flex direction="column" align="start" justify="between" style={{ height: '1.6rem', width: '5.24rem' }}>
                    <div className="store-name">
                        小易爱车
                    </div>
                    <Flex className="address" style={{ width: "100%" }}>
                        <div className="address-icon"></div>
                        <p className="info-font">南京市苏宁诺富特酒店B2</p>
                        <div className="icon"></div>
                    </Flex>
                    <Flex className="time" align="end" style={{ width: "100%" }}>
                        <div>
                            <Flex className="info-font">
                                <div className="time-icon"></div>
                                营业时间：8:20-20:00
                                </Flex>
                            <div>
                                <span className="identification">免费安全监测</span>
                                <span className="identification">下雨保</span>
                            </div>
                        </div>
                        <div className="icon"></div>
                    </Flex>
                </Flex>
            </Flex>
            <Flex className="store-detail-title">
                <div className="sign"></div>
                <div className="title">优惠活动</div>
            </Flex>
            <div className="swiper-container" ref={self => this.swiperID2 = self}>
                <div className="swiper-wrapper ">
                    <div className="swiper-slide cooperative-store-coupon">
                        <Flex className="coupon" direction="column" align="start">
                            <Flex style={{ height: '1.16rem', background: '#fff', width: '100%' }}>
                                <div className="money">￥25</div>
                                <div className="parting-line"></div>
                                <Flex style={{ flex: 'auto' }}>
                                    <Flex direction="column" align="start">
                                        <div>洗车抵扣券</div>
                                        <div>洗车项目时可直接抵扣</div>
                                    </Flex>
                                    <div className="use-button">
                                        立即使用
                                    </div>
                                </Flex>
                            </Flex>
                            <div className="coupon-info">
                                <span className="phone">限客户手机号：13951775978</span>
                                <span className="time">截止日期：2018.11.11</span>
                            </div>
                        </Flex>
                    </div>
                    <div className="swiper-slide cooperative-store-coupon">
                        <Flex className="coupon" direction="column" align="start">
                            <Flex style={{ height: '1.16rem', background: '#fff', width: '100%' }}>
                                <div className="money">￥1000</div>
                                <div className="parting-line"></div>
                                <Flex style={{ flex: 'auto', marginLeft: '.2rem' }}>
                                    <Flex direction="column" align="start">
                                        <div>洗车抵扣券</div>
                                        <div>洗车项目时可直接抵扣</div>
                                    </Flex>
                                    <div className="use-button">
                                        立即使用
                                    </div>
                                </Flex>
                            </Flex>
                            <div className="coupon-info">
                                <span className="phone">限客户手机号：13951775978</span>
                                <span className="time">截止日期：2018.11.11</span>
                            </div>
                        </Flex>
                    </div>
                </div>
            </div>
            <div style={{ height: '.21rem', background: '#fff', marginTop: '.04rem', borderBottom: '1px solid #dfdfe1' }}>
            </div>
            <Tabs defaultActiveKey="1" swipeable underlineColor="#5a88e5" className="store-service">
                <TabPane tab='门店服务' key="1" >
                    <Tabs defaultActiveKey="1" swipeable underlineColor="#5a88e5" className="tabpane1" >
                        <TabPane tab='汽车美容' key="1" style={{ borderTop: '1px solid #dfdfe1' }} >
                            <Flex style={{ width: '100%', borderBottom: '1px solid #dfdfe1' }} >
                                <div >
                                    <Flex direction="column" align="start" justify="center" style={{ height: '1.3rem' }}>
                                        <div className="beauty-title">普洗</div>
                                        <div className="beauty-aim">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘</div>
                                    </Flex>
                                </div>
                                <div className="money">
                                    ￥1000
                                </div>
                            </Flex>
                            <Flex style={{ width: '100%', borderBottom: '1px solid #dfdfe1' }} >
                                <div >
                                    <Flex direction="column" align="start" justify="center" style={{ height: '1.3rem' }}>
                                        <div className="beauty-title">普洗</div>
                                        <div className="beauty-aim">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘</div>
                                    </Flex>
                                </div>
                                <div className="money">
                                    ￥25
                                </div>
                            </Flex>
                        </TabPane>
                        <TabPane tab='维修保养' key="2" style={{ borderTop: '1px solid #dfdfe1' }}>
                            <Flex style={{ width: '100%', borderBottom: '1px solid #dfdfe1' }} >
                                <div >
                                    <Flex direction="column" align="start" justify="center" style={{ height: '1.3rem' }}>
                                        <div className="beauty-title">普洗</div>
                                        <div className="beauty-aim">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘</div>
                                    </Flex>
                                </div>
                                <div className="money">
                                    ￥25
                                </div>
                            </Flex>
                            <Flex style={{ width: '100%',borderBottom:'1px solid #dfdfe1' }} >
                                <div >
                                    <Flex direction="column" align="start" justify="center" style={{height:'1.3rem'}}>
                                        <div className="beauty-title">普洗</div>
                                        <div className="beauty-aim">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘</div>
                                    </Flex>
                                </div>
                                <div className="money">
                                    ￥25
                                </div>
                            </Flex>
                        </TabPane>
                    </Tabs>
                </TabPane>
                <TabPane tab='门店评价' key="2" className="tabpane2">
                    <Flex className="comment" align="start">
                        <div className="avatar"><img/></div>
                        <Flex.Item>
                            <div style={{width:'100%'}}><span className="phone">1559****5565</span><span className="time">2017-08-23</span></div>
                           <Star number={1}> </Star>
                           <div className="introduction">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘 </div>
                        </Flex.Item>
                    </Flex>
                    <Flex className="comment" align="start">
                        <div className="avatar"><img/></div>
                        <Flex.Item>
                            <div style={{width:'100%'}}><span className="phone">1559****5565</span><span className="time">2017-08-23</span></div>
                           <Star number={1}> </Star>
                           <div className="introduction">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘 </div>
                        </Flex.Item>
                    </Flex>
                </TabPane>
            </Tabs>
        </div>
    }

}
export default CooperativeStore