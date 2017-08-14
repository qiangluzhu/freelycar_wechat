import React from 'react';
import './AddCar.less'
import { List, InputItem, WhiteSpace, Picker } from 'antd-mobile'
import { createForm } from 'rc-form'
import NavBar from '../../components/NavBar'
const Item = List.Item;
class AddCar extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false,
            data: [{label:'B',value:'B'},{value:'A',label:'A'},{value:'C',label:'C'},{value:'D',label:'D'},{value:'E',label:'E'}],
            province:'A',
            city:'A'
        }
    }

    componentDidMount() {
        console.log(document.documentElement.clientWidth)
    }
    render() {
        const { getFieldProps } = this.props.form;
        return <div className="body-bac">
            <NavBar title="添加爱车" />
            <List className="add-car-info">

                <InputItem
                    {...getFieldProps('number') }
                    type="number"
                    clear
                    placeholder="填写车牌号"
                    maxLength="5"
                    labelNumber="6"
                >
                    <div style={{ display: 'inline-block' }}>车牌号</div>
                    <div className="card-number" style={{ display: 'inline-block',marginLeft:' 2.45rem' }}>
                        <Picker
                            data={this.state.data}
                            title="选择地区"
                            onOk={v => this.setState({ province: v })}
                            value={this.state.province}
                        >
                            <List.Item arrow="down" style={{ display: 'inline-block' }}></List.Item>
                        </Picker>
                    </div><span className="parting-line">|</span>
                    <div className="card-number" style={{ display: 'inline-block' }}>
                        <Picker
                            data={this.state.data}
                            title="选择城市"
                            value={this.state.city}
                            onOk={v => this.setState({ city: v })}
                        >
                            <List.Item arrow="down" style={{ display: 'inline-block' }}></List.Item>
                        </Picker>
                    </div><span className="parting-line">|</span>
                </InputItem>
                <Item extra="请选择品牌车系" arrow="horizontal" onClick={() => { }}>品牌车系</Item>
                <Item extra="点击添加车型" arrow="horizontal" onClick={() => { }}>车型</Item>
            </List>
            <div className="addCar-btn">保存</div>
        </div>
    }

}
const AddCarInfo = createForm()(AddCar);
// ReactDOM.render(<AddCarInfo />, mountNode);
export default AddCarInfo