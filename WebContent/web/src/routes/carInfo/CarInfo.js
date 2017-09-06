import React from 'react';
import './CarInfo.less'
import { List, InputItem, WhiteSpace, Picker, Flex, Icon, Switch, DatePicker } from 'antd-mobile'
import { createForm } from 'rc-form'
import NavBar from '../../components/NavBar'
import car_icon from '../../img/car_icon.jpg'
import insurance from '../../img/insurance.png'
import annualInspection from '../../img/annualInspection.png'
import more_arrow from '../../img/more_arrow.png'
import { myCar } from '../../services/user.js'
import { browserHistory } from 'dva/router'

class CarInfo extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            insuranceSwitch: false,//保险提醒开关
            insuranceTip: false,//保险tip
            annualInspection: false,//年检开关
            inspectionTip: false,//年检tip
            cars: [],
            inspectionTime: '',//年检提醒
            currentIndex: 0,//当前索引
            defaultIndex:0,//默认索引
        }
    }

    componentDidMount() {

        myCar({
            clientId: 1,
        }).then((res) => {
            console.log(res);
            if (res.data.code == '0') {
                let data = res.data.data;
                this.setState({
                    cars: data
                })
                //加载swiper
                let mySwiper = new Swiper(this.swiperID, {
                    direction: 'horizontal',
                    loop: false,
                    slidesPerView: 1.2,
                    initialSlide :this.state.defaultIndex,
                    slideToClickedSlide: true,
                    grabCursor: true,
                    slidesPerGroup: 1,
                    spaceBetween: 5,
                    centeredSlides: false,
                    onSlideChangeEnd: (swiper) => {
                        this.setState({
                            currentIndex: swiper.activeIndex
                        });
                    }
                    // 如果需要分页器
                });
            }
        }).catch((error) => { console.log(error) });
    }


    setDefaultIndex = (index) =>{
        this.setState({
            defaultIndex:index
        });
    }


    //处理保险提醒
    OnHanleinsurance = (checked) => {
        this.setState({
            insuranceSwitch: checked,
            insuranceTip: !this.state.insuranceTip
        });
    }

    //处理年检提醒
    OnHanleAnnualInspection = (checked) => {
        this.setState({
            annualInspection: checked,
            inspectionTip: !this.state.inspectionTip
        });
    }

    render() {

        const carlist = this.state.cars.map((item, index) => {
            return <Flex key={index} className="swiper-slide carItem">
                <img className='car_icon' src={car_icon} alt="" />
                <div>
                    <div className='licensePlate'>{item.car.licensePlate}</div>
                    <div className='type'>{item.car.carbrand}</div>
                </div>
                <div className={index==this.state.defaultIndex?'check':'no-check'} onClick={()=>{this.setDefaultIndex(index)}}>
                    <Icon type='check-circle-o' />
                    <div className={index==this.state.defaultIndex?'':'hidden'}>默认</div>
                </div>

            </Flex>
        });

        let cars = this.state.cars;
        let time = 0;
        if(cars.length>0){
            time = cars[0].time;
        }

        return <div className="body-bac">
            <NavBar title="爱车信息" />
            <div style={{ margin: '0 10px' }}>
                <div className="swiper-container carInfo" ref={self => this.swiperID = self}>
                    <div className="swiper-wrapper">
                        {carlist}
                        <div className="swiper-slide addItem" onClick={()=>{ browserHistory.push('/addCar')}} >+</div>
                    </div>
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
                <Flex className='remind-tip' style={{ display: this.state.insuranceTip ? '' : 'none' }}>
                    <div>距离下次续保时间还有<span className='day'>{time}</span>天</div>
                    <img src={more_arrow} alt="" className='more' />
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

                <div style={{ display: this.state.inspectionTip ? '' : 'none' }}>
                    <DatePicker
                        mode="date"
                        title="选择日期"
                        extra=""
                        value={this.state.inspectionTime}
                        onChange={(e) => { this.setState({ inspectionTime: e }) }}
                    >
                        <List.Item arrow="horizontal">请选择上一次年检的时间</List.Item>
                    </DatePicker>
                </div>
                {/* <Flex className='remind-tip' style={{ display: this.state.inspectionTip ? '' : 'none' }}>
                    <div>距离下次续保时间还有<span className='day'>28</span>天</div>
                    <img src={more_arrow} alt="" className='more' />
                </Flex> */}
            </List>

        </div>
    }

}
export default CarInfo