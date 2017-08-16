import React from 'react';
import NavBar from '../../components/NavBar'
import { Flex } from 'antd-mobile'
import './MyCard.less'
class MyPoints extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
        }
    }

    render() {

        return <div>
            <NavBar title="我的积分" />
            <div className="my-total-point">
                <Flex className="circle" justify="center" direction="column" >
                    <div>积分</div>
                    <div>2000</div>
                </Flex>
            </div>
            <div className="my-total-point-content">
                <div className="wait">
                    敬请期待...
                </div>
                <div className="title">
                    积分明细
                </div>
                <Flex className="detail">
                    <Flex direction="column" align="start">
                        <div className="comment">
                            订单评价积分
                        </div>
                        <div className="time">
                            2017.05.23
                        </div>
                    </Flex>
                    <Flex.Item className="money">
                        +25
                    </Flex.Item>
                </Flex>
            </div>
        </div>
    }
}
export default MyPoints