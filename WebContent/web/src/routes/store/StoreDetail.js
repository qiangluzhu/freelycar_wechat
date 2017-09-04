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
                <Flex direction="column" align="start" justify="between" style={{ height: '1.6rem', }}>
                    <div className="store-name">
                        小易爱车   <span style={{ fontSize: '.18rem', color: '#e42f2f', marginLeft: '.04rem' }}>5.0分</span>
                    </div>
                    <Flex className="address">
                        <div className="address-icon"></div>
                        <p className="info-font">南京市苏宁诺富特酒店B2</p>

                    </Flex>
                    <Flex className="time" align="end" >
                        <div>
                            <Flex className="info-font">
                                <div className="time-icon"></div>
                                营业时间：8:20-20:00
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
                    <div className="tel-icon"></div>
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
                                <div className="money"><span style={{ fontSize: '.18rem' }}>￥</span>25</div>
                                <div className="parting-line"></div>
                                <Flex style={{ flex: 'auto' }}>
                                    <Flex direction="column" align="start">
                                        <div style={{ fontSize: '.3rem', marginLeft: '.2rem' }}>洗车抵扣券</div>
                                        <div style={{ fontSize: '.2rem', lineHeight: '.3rem', marginLeft: '.2rem' }}>洗车项目时可直接抵扣</div>
                                    </Flex>
                                    <Flex className="use-button">
                                        <div className="use-button-plus">-</div>
                                        <div className="number">1</div>
                                        <div className="use-button-add">+</div>
                                    </Flex>
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
                                <div className="money"><span style={{ fontSize: '.18rem' }}>￥</span>1000</div>
                                <div className="parting-line"></div>
                                <Flex style={{ flex: 'auto', marginLeft: '.2rem' }}>
                                    <Flex direction="column" align="start">
                                        <div style={{ fontSize: '.3rem', marginLeft: '.2rem' }}>洗车抵扣券</div>
                                        <div style={{ fontSize: '.2rem', lineHeight: '.3rem', marginLeft: '.2rem' }}>洗车项目时可直接抵扣</div>
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
            <div style={{ height: '.21rem', background: '#fff', marginTop: '.04rem' }}>
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
                                    <span style={{ fontSize: '.18rem' }}>￥</span>1000
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
                                    <span style={{ fontSize: '.18rem' }}>￥</span>25
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
                                    <span style={{ fontSize: '.18rem' }}>￥</span>25
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
                                    <span style={{ fontSize: '.18rem' }}>￥</span>25
                                </div>
                            </Flex>
                        </TabPane>
                    </Tabs>
                </TabPane>
                <TabPane tab='门店评价' key="2" className="tabpane2">
                    <Flex className="comment" align="start">
                        <div className="avatar"><img /></div>
                        <Flex.Item>
                            <div style={{ width: '100%' }}><span className="phone">1559****5565</span><span className="time">2017-08-23</span></div>
                            <Star number={1}> </Star>
                            <div className="introduction">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘 </div>
                        </Flex.Item>
                    </Flex>
                    <Flex className="comment" align="start">
                        <div className="avatar"><img /></div>
                        <Flex.Item>
                            <div style={{ width: '100%' }}><span className="phone">1559****5565</span><span className="time">2017-08-23</span></div>
                            <Star number={1}> </Star>
                            <div className="introduction">整车泡沫冲洗，车内洗尘，内饰简单清洁除尘 </div>
                        </Flex.Item>
                    </Flex>
                </TabPane>
            </Tabs>
            <div className='bottom-pay-button'>
                <Flex style={{ height: '100%' }}>
                    <Flex.Item className='lable'>合计:</Flex.Item>
                    <Flex.Item style={{color:'red'}}>￥999</Flex.Item>
                    <div className='pay-button'>
                        <Flex style={{ height: '100%' }}>
                            <Flex.Item style={{textAlign:'center',color:'#fff'}}>立即购买</Flex.Item>
                        </Flex>
                    </div>
                </Flex>
            </div>

        </div>
    }

}
export default CooperativeStore