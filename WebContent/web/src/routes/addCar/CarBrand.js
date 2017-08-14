import { province } from 'antd-mobile-demo-data';
import { ListView, List, Flex,Drawer} from 'antd-mobile';
import NavBar from '../../components/NavBar'
import DasAuto from '../../img/das_auto.jpg'
import fute from '../../img/fute.jpg'
const { Item } = List;

class SelectCarBrand extends React.Component {
    constructor(props) {
        super(props);
        const getSectionData = (dataBlob, sectionID) => dataBlob[sectionID];
        const getRowData = (dataBlob, sectionID, rowID) => dataBlob[rowID];

        const dataSource = new ListView.DataSource({
            getRowData,
            getSectionHeaderData: getSectionData,
            rowHasChanged: (row1, row2) => row1 !== row2,
            sectionHeaderHasChanged: (s1, s2) => s1 !== s2,
        });

        const dataBlob = {};
        const sectionIDs = [];
        const rowIDs = [];
        Object.keys(province).forEach((item, index) => {
            sectionIDs.push(item);
            dataBlob[item] = item;
            rowIDs[index] = [];
            province[item].forEach((jj) => {
                rowIDs[index].push(jj.value);
                dataBlob[jj.value] = jj.label;
            });
        });
        this.state = {
            dataSource: dataSource.cloneWithRowsAndSections(dataBlob, sectionIDs, rowIDs),
            headerPressCount: 0,
            open:true,
            hotlist: [{ img: DasAuto, name: '大众' }, { img:fute, name: '福特' }, { img: '', name: '本田' }, { img: '', name: '丰田' }, { img: '', name: '别克' }, { img: '', name: '奥迪' }]
        };
    }

    componentDidMount() {
        console.log(province)
        console.log(this.state.dataSource)
    }

    onOpenChange = (...args) => {
        console.log(args);
        this.setState({ open: !this.state.open });
    }

    render() { 
        let hotlist = this.state.hotlist.map((item, index) => {
        return <Flex key={index} className={index>4?'one-item border-no':'one-item'} direction="column">
                <img src={item.img} alt="" />
                <div>{item.name}</div>
            </Flex>
        })
        const sidebar = (<List>
            {[...Array(20).keys()].map((i, index) => {
              if (index === 0) {
                return (<List.Item key={index}
                  multipleLine
                >Category</List.Item>);
              }
              return (<List.Item key={index}
              >Category{index}</List.Item>);
            })}
          </List>);
        return (
            <div className="body-bac">
                <NavBar title="请选择品牌" />

                <ListView.IndexedList
                    className="car-brand-header-box "
                    dataSource={this.state.dataSource}
                    renderHeader={() => <div>
                        <div style={{ width: '100%',  color: '#808080', background: '#fff', height: '.6rem', lineHeight: '.6rem',marginBottom:'2px' }}><span style={{marginLeft:'12px'}}>热门品牌</span></div>
                        <Flex className="car-brand-head" wrap="wrap">
                            {hotlist}
                        </Flex>
                    </div>}
                    renderFooter={() => <span>custom footer</span>}
                    renderSectionHeader={sectionData => (<div className="ih">{sectionData}</div>)}
                    renderRow={rowData => (<Item onClick={(e)=>{this.onOpenChange(e)}}>{rowData}</Item>)}
                    style={{
                        height: document.documentElement.clientHeight ,
                        overflow: 'auto',
                    }}
                    quickSearchBarStyle={{
                        position: 'absolute',
                        top: 150,
                    }}
                    delayTime={10}
                    delayActivityIndicator={<div style={{ padding: 25, textAlign: 'center' }}>rendering...</div>}
                />
                <Drawer
                    className="my-drawer"
                    style={{ minHeight: document.documentElement.clientHeight - 200 }}
                    enableDragHandle
                    position={"right"}
                    contentStyle={{ color: '#A6A6A6', textAlign: 'center', paddingTop: 42 }}
                    sidebar={sidebar}
                    open={this.state.open}
                    onOpenChange={(e)=>{this.onOpenChange(e)}}
                >
                </Drawer>
            </div>
        );
    }
}

export default SelectCarBrand