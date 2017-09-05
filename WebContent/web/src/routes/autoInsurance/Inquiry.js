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
            city: ['B'],
            province: '苏',
            carModel: '',
            carPlate: ''
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

    PopupModal() {
        
                let province = ['京', '沪', '浙', '苏', '粤', '鲁', '晋', '冀', '豫', '川', '渝', '辽', '吉', '黑', '皖', '鄂', '湘', '赣', '闽', '陕', '甘', '宁', '蒙', '津', '贵', '云', '桂', '琼', '青', '新', '藏'];
                let items = province.map((item, index) => {
                    return <li key={index} onClick={() => { this.selectProvince(item) }} style={{ float: 'left', color: this.state.province == item ? '#fff' : '#666', background: this.state.province == item ? '#1e1e1e' : '#fff', width: '.915rem', height: '.915rem', textAlign: 'center', lineHeight: '.92rem', borderRight: '1px solid #eee', borderBottom: '1px solid #eee' }}>{item}</li>
                })
                Popup.show(<Flex justify="center" align="center" style={{ background: '#eee' }}>
                    <ul className="clear" style={{ listStyle: 'none', width: '100%', borderTop: '1px solid #eee', borderLeft: '1px solid #eee' }}>
                        {items}
                    </ul>
                </Flex>, { animationType: 'slide-up', maskClosable: true });
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
                clear
                placeholder="填写车牌号"
                style={{marginLeft:'.24rem'}}
                maxLength="6"
                labelNumber="6"
                onChange={(e) => { this.setState({ carPlate: e }) }}
            >
                <div style={{ display: 'inline-block' }}>车牌号</div>
                <div className="card-number" style={{ display: 'inline-block', marginLeft: ' 3.3rem' }}>
                    <List.Item extra={this.state.province} arrow="down" style={{ display: 'inline-block' }} onClick={() => { this.PopupModal() }}></List.Item>
                </div>
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

