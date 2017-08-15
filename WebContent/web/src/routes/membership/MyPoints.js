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
                <Flex className="circle" align="center" direction="column" >
                    <div>积分</div>
                    <div>2000</div>
                </Flex>
            </div>
        </div>
    }
}
export default MyPoints