import React from 'react';
import NavBar from '../../components/NavBar'
import './Inquiry.less'
import { List, InputItem, WhiteSpace, Picker,Flex } from 'antd-mobile'
const Item = List.Item;
class Inquiry extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            data: [{ label: 'B', value: 'B' }, { value: 'A', label: '苏' }, { value: 'C', label: 'C' }, { value: 'D', label: 'D' }, { value: 'E', label: 'E' }],
            province: ['A'],
            city: ['B']
        }
    }
    componentDidMount() {
        let mySwiper4= new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: true,
            // 如果需要分页器
            pagination: '.swiper-pagination',
        });
    }
    render() {
        return <div>

            <NavBar title={'车险询价'}></NavBar>
            <div className="swiper-container" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    <div className="swiper-slide  banner-img ">Slide 1</div>
                    <div className="swiper-slide  banner-img">Slide 2</div>
                </div>
                <div className="swiper-pagination circle-color"></div>
            </div>
            <List className="add-car-info autoInsurance-Inquiry">
                <InputItem
                    clear
                    placeholder="填写车主姓名"
                >
                    <div style={{ display: 'inline-block', marginLeft: '.24rem' }}>车主姓名</div>

                </InputItem>
                <InputItem
                    type="number"
                    clear
                    placeholder="填写车牌号"
                    maxLength="5"
                    labelNumber="6"
                >
                    <div style={{ display: 'inline-block', marginLeft: '.24rem' }}>车牌号</div>
                    <div className="card-number" style={{ display: 'inline-block', marginLeft: ' 2.25rem' }}>
                        <Picker
                            data={this.state.data} cols={1}
                            value={this.state.province}
                            onOk={v => this.setState({ province: v + '' })}
                        >
                            <List.Item arrow="down" style={{ display: 'inline-block' }}></List.Item>
                        </Picker>
                    </div><span className="parting-line">|</span>
                    <div className="card-number" style={{ display: 'inline-block' }}>
                        <Picker
                            data={this.state.data} cols={1}
                            value={this.state.city}
                            onOk={v => this.setState({ city: v + '' })}
                        >
                            <List.Item arrow="down" style={{ display: 'inline-block' }}></List.Item>
                        </Picker>
                    </div><span className="parting-line">|</span>
                </InputItem>
                <InputItem
                    clear
                    type="number"
                    placeholder="填写手机号码"
                    style={{ textAlign: 'right' }}
                >
                    <div style={{ display: 'inline-block', marginLeft: '.24rem' }}>手机号码</div>
                </InputItem>
                <Picker data={this.state.data} cols={1} placeholder="选填">
                    <List.Item arrow="horizontal" style={{ marginLeft: '.24rem' }}>保险公司</List.Item>
                </Picker>
            </List>
            <div className="inquiry-button">
                <span>立即询价 ></span>
                <span className="secret">你的信息将被严格保密</span>
            </div>
            <div className="cooperative-agency">
                <div className="title">合作机构</div>
                <div className="list-body clear" >
                    <div className="list-item"></div>
                    <div className="list-item"></div>
                    <div className="list-item"></div>
                    <div className="list-item"></div>
                    <div className="list-item"></div>
                </div>
            </div>
        </div>
    }
}

export default Inquiry

