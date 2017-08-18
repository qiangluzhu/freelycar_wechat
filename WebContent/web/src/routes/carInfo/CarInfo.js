import React from 'react';
import './CarInfo.less'
import { List, InputItem, WhiteSpace, Picker, Flex, Icon, Switch } from 'antd-mobile'
import { createForm } from 'rc-form'
import NavBar from '../../components/NavBar'
import car_icon from '../../img/car_icon.jpg'
import insurance from '../../img/insurance.png'
import annualInspection from '../../img/annualInspection.png'
import more_arrow from '../../img/more_arrow.png'

class CarInfo extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            insuranceSwitch: false,//保险提醒开关
            insuranceTip: false,//保险tip
            annualInspection: false,//年检开关
            inspectionTip: false,//年检tip
        }
    }

    componentDidMount() {
        let mySwiper = new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: false,
            slidesPerView: 1.2,
            slidesPerGroup: 1,
            spaceBetween: 5,
            slidesOffsetBefore: 11,
            centeredSlides: false,
            slidesOffsetAfter: -265,
            // 如果需要分页器
        });
    }


    //处理保险提醒
    OnHanleinsurance = (checked) => {
        this.setState({
            insuranceSwitch: checked,
            insuranceTip:!this.state.insuranceTip
        });
    }

    //处理年检提醒
    OnHanleAnnualInspection = (checked) => {
        this.setState({
            annualInspection: checked,
           inspectionTip:!this.state.inspectionTip
        });
    }

    render() {
        return <div className="body-bac">
            <NavBar title="爱车信息" />


            <div className="swiper-container carInfo" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    <Flex className="swiper-slide carItem" >
                        <img className='car_icon' src={car_icon} alt="" />
                        <div>
                            <div className='licensePlate'>苏A12345</div>
                            <div className='type'>奔驰 xxxx</div>
                        </div>
                        <div className='check'>
                            <Icon type='check-circle-o' />
                            <div>默认</div>
                        </div>

                    </Flex>
                    <Flex className="swiper-slide carItem" >
                        <img className='car_icon' src={car_icon} alt="" />
                        <div>
                            <div className='licensePlate'>苏A12345</div>
                            <div className='type'>奔驰 xxxx</div>
                        </div>
                        <Icon className='check' type='check-circle-o' />
                    </Flex>
                    <Flex className="swiper-slide carItem" >
                        <img className='car_icon' src={car_icon} alt="" />
                        <div>
                            <div className='licensePlate'>苏A12345</div>
                            <div className='type'>奔驰 xxxx</div>
                        </div>
                        <Icon className='check' type='check-circle-o' />
                    </Flex>
                    <div className="swiper-slide addItem" >+</div>
                </div>
            </div>



            <List className='remind-item' style={{ margin: '10px' }}>
                <List.Item extra={<Switch checked={this.state.insuranceSwitch}
                    onClick={(checked) => { this.OnHanleinsurance(checked) }} />}>
                    <Flex>
                        <img className='icon' src={insurance} alt="" />
                        保险提醒
                    </Flex>
                </List.Item>
                <Flex className='remind-tip' style={{display:this.state.insuranceTip?'':'none'}}>
                    <div>距离下次续保时间还有<span className='day'>28</span>天</div>
                    <img src={more_arrow} alt="" className='more'/>
                </Flex>
            </List>

            <List className='remind-item' style={{ margin: '10px' }}>
                <List.Item extra={<Switch checked={this.state.annualInspection}
                    onClick={(checked) => { this.OnHanleAnnualInspection(checked) }} />}>
                    <Flex>
                        <img className='icon' src={annualInspection} alt="" />
                        年检提醒
                    </Flex>
                </List.Item>
                <Flex className='remind-tip' style={{display:this.state.inspectionTip?'':'none'}}>
                    <div>距离下次续保时间还有<span className='day'>28</span>天</div>
                    <img src={more_arrow} alt="" className='more'/>
                </Flex>
            </List>


        </div>
    }

}
export default CarInfo