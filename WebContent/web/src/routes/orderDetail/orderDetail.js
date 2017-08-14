import React from 'react';

import { Flex } from 'antd-mobile'
import NavBar from '../../components/NavBar'
class OrderDetail extends React.Component {

    constructor(props) {
        super(props)
        this.state = {

        }
    }

    render() {
        return <div className="body-bac">
            <NavBar title="添加爱车" />
        </div>
    }
}

export default OrderDetail