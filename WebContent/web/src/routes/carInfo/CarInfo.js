import React from 'react';
import './CarInfo.less'
import { List, InputItem, WhiteSpace, Picker, Flex, Icon, Switch, DatePicker, Popover } from 'antd-mobile'
import { createForm } from 'rc-form'
import NavBar from '../../components/NavBar'
import car_icon from '../../img/car_icon.jpg'
import insurance from '../../img/insurance.png'
import annualInspection from '../../img/annualInspection.png'
import more_arrow from '../../img/more_arrow.png'
import { myCar, defaultCar, annualCheck, delCar } from '../../services/user.js'
import PropTypes from 'prop-types';
let Item = List.Item
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
            defaultIndex: 0,//默认索引
            mode: '',
            visible: false
        }
    }

    componentDidMount() {
        myCar({
            clientId: window.localStorage.getItem('clientId'),
        }).then((res) => {
            if (res.data.code == '0') {
                let data = res.data.data;

                //遍历找出默认的车辆
                let defaultIndex = 0;
                for (let d of data) {
                    if (d.car.defaultCar) {
                        defaultIndex++;
                    }
                }

                this.setState({
                    cars: data,
                    defaultIndex: defaultIndex
                })
                //加载swiper
                let mySwiper = new Swiper(this.swiperID, {
                    direction: 'horizontal',
                    loop: false,
                    slidesPerView: 1.2,
                    initialSlide: this.state.defaultIndex,
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


    setDefaultIndex = (id, index) => {
        this.setState({
            defaultIndex: index
        });

        defaultCar({
            carId: id
        }).then((res) => {
            console.log(res);

        }).catch((error) => { console.log(error) });
    }

    delCar = (id) => {
        delCar({
            carId: id
        }).then((res) => {
            console.log(res)
            if (res.data.code == '0') {
                this.setState({
                    mode:''
                })
            }
        })
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
        let offsetX = -10; // just for pc demo
        if (/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent)) {
            offsetX = -26;
        }
        const carlist = this.state.cars.map((item, index) => {
            return <Flex key={index} className="swiper-slide carItem">
                {this.state.mode && <span style={{ width: '.6rem', margin: '0 .18rem', textAlign: 'center', height: '.42rem', fontSize: '.22rem', lineHeight: '.42rem', background: '#e42f2f', color: '#fff' }} onClick={() => { this.delCar(item.car.id) }}>删除</span>}
                <img className='car_icon' style={{ marginLeft: this.state.mode ? '0' : '.54rem' }} src={car_icon} alt="" />
                <div>
                    <div className='licensePlate'>{item.car.licensePlate}</div>
                    <div className='type'>{item.car.carbrand}</div>
                </div>
                <div className={index == this.state.defaultIndex ? 'check' : 'no-check'} onClick={() => { this.setDefaultIndex(item.car.id, index) }}>
                    <Icon type='check-circle-o' />
                    <div className={index == this.state.defaultIndex ? '' : 'hidden'}>默认</div>
                </div>

            </Flex>
        });

        let cars = this.state.cars;
        let time = 0;
        let carId = -1;
        if (cars.length > 0) {
            time = cars[this.state.currentIndex].time;
            carId = cars[this.state.currentIndex].car.id;
        }

        return <div className="body-bac">
            <div className="nav-bar-title">
                <i className="back" onClick={() => { this.context.router.history.push(`/center/${window.localStorage.getItem('openid')}/${window.localStorage.getItem('nickName')}/${window.localStorage.getItem('headimgurl')}`) }}></i>
                爱车信息
            <span className="scan" onClick={() => {
                    this.setState({
                        visible: !this.state.visible
                    })
                }}>编辑</span>
            </div>
            <Popover mask
                overlayClassName="fortest"
                overlayStyle={{ color: 'currentColor' }}
                visible={this.state.visible}
                overlay={[
                    (<Item key="4" value="scan" data-seed="logId" onClick={
                        () => {
                            this.setState({
                                mode: 'delete',
                                visible: !this.state.visible
                            })
                        }
                    } >删除</Item>),
                ]}
                align={{
                    overflow: { adjustY: 0, adjustX: 0 },
                    offset: [offsetX, 15],
                }}
                onVisibleChange={this.handleVisibleChange}
                onSelect={this.onSelect}
            >
                <div style={{
                    height: '100%',
                    padding: '0 0.3rem',
                    marginRight: '-0.3rem',
                    display: 'flex',
                    alignItems: 'center',
                }}
                >
                </div>
            </Popover>
            <div style={{ margin: '0 10px' }}>
                <div className="swiper-container carInfo" ref={self => this.swiperID = self}>
                    <div className="swiper-wrapper">
                        {carlist}
                        <div className="swiper-slide addItem" onClick={() => { this.context.router.history.push('/addCar/0') }} >+</div>
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
                <Flex className='remind-tip' style={{ display: this.state.insuranceTip ? '' : 'none' }} onClick={() => { this.context.router.history.push(`/insurance/${carId}`) }}>
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

                <div style={{display: this.state.inspectionTip ? '' : 'none' }}>
                    <DatePicker
                        mode="date"
                        title="选择日期"
                        extra=""
                        value={this.state.inspectionTime}
                        onChange={(e) => {
                            annualCheck({
                                //clientId: 157,
                                clientId: window.localStorage.getItem('clientId'),
                                id: carId,
                                licenseDate: e.format('YYYY-MM-DD'),
                            }).then((res) => {
                                if (res.data.code == '0') {
                                    let cars = this.state.cars;
                                    let car = cars[this.state.currentIndex];
                                    car.day = res.data.day;
                                    this.setState({
                                        cars: cars
                                    })
                                }
                            }).catch((error) => { console.log(error) });
                            // this.setState({ inspectionTime: e })
                        }}
                    >
                        <List.Item arrow="horizontal">
                            <span style={{ fontSize: '.8em', marginLeft: '.27rem', display: (this.state.inspectionTip && this.state.cars[this.state.currentIndex].day == -1) ? '' : 'none'}}>请选择车辆注册日期</span>
                            <span style={{ fontSize: '.8em', marginLeft: '.27rem', display: (this.state.inspectionTip && this.state.cars[this.state.currentIndex].day >= 0) ? '' : 'none' }}>距离下次续保时间还有<span className='day'>{this.state.cars.length > 0 ? this.state.cars[this.state.currentIndex].day : 0}</span>天</span>
                        </List.Item>


                    </DatePicker>
                </div>

                {/* <Flex className='remind-tip' style={{ display: (this.state.inspectionTip && this.state.cars[this.state.currentIndex].day >= 0) ? '' : 'none' }}>
                    <div>距离下次续保时间还有<span className='day'>{this.state.cars.length > 0 ? this.state.cars[this.state.currentIndex].day : 0}</span>天</div>
                    <img src={more_arrow} alt="" className='more' />
                </Flex> */}
            </List>

        </div>
    }
}
export default CarInfo
CarInfo.contextTypes = {
    router: PropTypes.object.isRequired
}