import React from 'react';
import { Tabs, WhiteSpace, Badge, Flex } from 'antd-mobile';
import more_arrow from '../../img/more_arrow.png'
import {listConsumOrder,listWXPayOrder} from '../../services/orders.js'

const TabPane = Tabs.TabPane;

function callback(key) {
    console.log('onChange', key);
}
function handleTabClick(key) {
    console.log('onTabClick', key);
}

class ServiceCard extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false
        }
    }

    componentDidMount() {
        listConsumOrder({
            clientId:1,
            page:1,
            number:10
        }).then((res) => {
            console.log(res)
            if (res.data.code == '0') {
                let data = res.data.data
                // this.setState({
                //     point: data.point,
                //     name: data.name ? data.name : data.nickName,
                //     headimgurl: data.headimgurl
                // })
            }
        }).catch((error) => { console.log(error) });
    }


    render() {
        return <div>
            <Tabs defaultActiveKey="1" onChange={callback} onTabClick={handleTabClick}>
                <TabPane tab='服务' key="1">
                    <Flex className="center-listItem" direction="column">
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.24rem', color: '#4b4b4b' }}>
                            <i className="circle"></i>
                            <p>内饰除菌SPA</p>
                            <Flex.Item className="finish-state">已完成&nbsp;&nbsp;<img src={more_arrow} alt="" /></Flex.Item>
                        </Flex>
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.18rem', color: '#8e8e8e' }}>
                            <i className="circle2"></i>
                            <p>2017/07/25 14:23</p>
                            <Flex.Item className="total-price">￥200</Flex.Item >
                        </Flex>
                    </Flex>
                    <Flex className="center-listItem" direction="column">
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.24rem', color: '#4b4b4b' }}>
                            <i className="circle"></i>
                            <p>内饰除菌SPA</p>
                            <Flex.Item className="finish-state">已完成&nbsp;&nbsp;<img src={more_arrow} alt="" /></Flex.Item>
                        </Flex>
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.18rem', color: '#8e8e8e' }}>
                            <i className="circle2"></i>
                            <p>2017/07/25 14:23</p>
                            <Flex.Item className="total-price">￥200</Flex.Item >
                        </Flex>

                        <Flex style={{ width: '100%', fontSize: '.18rem', textAlign: 'right' }}>
                            <Flex.Item className="comments-div">
                                <div className='comments'>评价得30积分</div>
                            </Flex.Item >
                        </Flex>

                    </Flex>
                </TabPane>
                <TabPane tab='卡券' key="2">
                    <Flex className="center-listItem" direction="column">
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.24rem', color: '#4b4b4b' }}>
                            <i className="circle"></i>
                            <p>内饰除菌SPA</p>
                            <Flex.Item className="finish-state">已完成&nbsp;&nbsp;<img src={more_arrow} alt="" /></Flex.Item>
                        </Flex>
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.18rem', color: '#8e8e8e' }}>
                            <i className="circle2"></i>
                            <p>2017/07/25 14:23</p>
                            <Flex.Item className="total-price">￥200</Flex.Item >
                        </Flex>
                    </Flex>
                    <Flex className="center-listItem" direction="column">
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.24rem', color: '#4b4b4b' }}>
                            <i className="circle"></i>
                            <p>内饰除菌SPA</p>
                            <Flex.Item className="finish-state">已完成&nbsp;&nbsp;<img src={more_arrow} alt="" /></Flex.Item>
                        </Flex>
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.18rem', color: '#8e8e8e' }}>
                            <i className="circle2"></i>
                            <p>2017/07/25 14:23</p>
                            <Flex.Item className="total-price">￥200</Flex.Item >
                        </Flex>
                    </Flex>
                    <Flex className="center-listItem" direction="column">
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.24rem', color: '#4b4b4b' }}>
                            <i className="circle"></i>
                            <p>内饰除菌SPA</p>
                            <Flex.Item className="finish-state">已完成&nbsp;&nbsp;<img src={more_arrow} alt="" /></Flex.Item>
                        </Flex>
                        <Flex style={{ width: '100%', height: '.4rem', fontSize: '.18rem', color: '#8e8e8e' }}>
                            <i className="circle2"></i>
                            <p>2017/07/25 14:23</p>
                            <Flex.Item className="total-price">￥200</Flex.Item >
                        </Flex>
                    </Flex>
                </TabPane>
            </Tabs>
            <WhiteSpace />
        </div>
    }

}
export default ServiceCard