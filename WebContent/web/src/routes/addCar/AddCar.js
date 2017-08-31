import React from 'react';
import './AddCar.less'
import { Popup, List, InputItem, WhiteSpace, Picker, Button, Flex, Icon } from 'antd-mobile'
import { createForm } from 'rc-form'
import NavBar from '../../components/NavBar'
const Item = List.Item;
const isIPhone = new RegExp('\\biPhone\\b|\\biPod\\b', 'i').test(window.navigator.userAgent);
let maskProps;
if (isIPhone) {
    // Note: the popup content will not scroll.
    maskProps = {
        onTouchStart: e => e.preventDefault(),
    };
}
class AddCar extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false,
            data: [{ label: 'B', value: 'B' }, { value: 'A', label: '苏' }, { value: 'C', label: 'C' }, { value: 'D', label: 'D' }, { value: 'E', label: 'E' }],
            province: ['A'],
            city: ['B']
        }
    }

    componentDidMount() {
        console.log(document.documentElement.clientWidth)
    }

    selectProvince() {
        Popup.hide()
    }

    PopupModal() {
        
        let province = ['京', '沪', '浙', '苏', '粤', '鲁', '晋', '冀', '豫', '川', '渝', '辽', '吉', '黑', '皖', '鄂', '湘', '赣', '闽', '陕', '甘', '宁', '蒙', '津', '贵', '云', '桂', '琼', '青', '新', '藏'];
        let items = province.map((item, index) => {
            return <li key={index} onClick={() => { this.selectProvince() }} style={{ float: 'left', color: '#666', width: '.92rem', height: '.92rem', textAlign: 'center', lineHeight: '.92rem', borderRight: '1px solid #eee', borderBottom: '1px solid #eee' }}>{item}</li>
        })
        Popup.show(<Flex justify="center" align="center">
            <ul className="clear" style={{ listStyle: 'none', width: '6.6rem', borderTop: '1px solid #eee', borderLeft: '1px solid #eee' }}>
                {items}
            </ul>
        </Flex>, { animationType: 'slide-up', maskClosable:true,maskProps});
    }
    render() {
        const { getFieldProps } = this.props.form;
        return <div className="body-bac">
            <NavBar title="添加爱车" />
            <List className="add-car-info">

                <InputItem
                    {...getFieldProps('number') }
                    clear
                    placeholder="填写车牌号"
                    maxLength="6"
                    labelNumber="6"
                >
                    <div style={{ display: 'inline-block' }}>车牌号</div>
                    <div className="card-number" style={{ display: 'inline-block', marginLeft: ' 2.25rem' }}>
                        <List.Item extra="苏" arrow="down" style={{ display: 'inline-block' }} onClick={() => { this.PopupModal() }}></List.Item>
                    </div>
                    {/* <div className="card-number" style={{ display: 'inline-block' }}>
                        <Picker
                            data={this.state.data}   cols={1} {...getFieldProps('district3') }
                            value={this.state.city}
                            onOk={v => this.setState({ city: v + '' })}
                        >
                            <List.Item arrow="down" style={{ display: 'inline-block' }}></List.Item>
                        </Picker>
                    </div><span className="parting-line">|</span> */}
                </InputItem>
                <Item className="addcar-listItem" extra={window.localStorage.getItem('brandType') ? <span style={{ color: '#000' }}>{window.localStorage.getItem('brandType')}</span> : "请选择品牌车系"} arrow="horizontal" onClick={() => { }}>品牌车系</Item>

                <Picker
                    data={this.state.data}
                    title="车型"
                    onChange={v => this.setState({ sValue: v })}
                >
                    <Item className="addcar-listItem" extra="点击添加车型" arrow="horizontal" onClick={() => { }}>车型</Item>
                </Picker>

            </List>
            <div className="addCar-btn">保存</div>
        </div>
    }

}
const AddCarInfo = createForm()(AddCar);
// ReactDOM.render(<AddCarInfo />, mountNode);
export default AddCarInfo