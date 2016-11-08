import React, { Component } from 'react';
import {
  Text,
  TextInput,
  Navigator,
  View
} from 'react-native';
import { CustomButton } from './button.js'

export class AddItem extends Component {
  constructor(props) {
    super(props);
    this.state = { pulse: 'pulse value', feeling: 'how are you feeling?' };
  }

  addItem() {
    if (this.state.pulse != ''&& this.state.feling != '' && this.state.pulse == parseInt(this.state.pulse, 10))
    {
      this.props.store.push({
        "value": this.state.pulse,
        "feeling": this.state.feeling,
      });
      // Then return to first page after inserting
      this.props.navigator.pop();
    } else {
      alert("Something is not right!")
    }
  }

  render() {
    return(
      <View>
        <Text>LOG PULSE</Text>
          <View>
            <TextInput value={this.state.pulse}
               onFocus={() => this.setState({pulse: ''})}
               onChangeText={(pulse) => this.setState({pulse})}/>
             <TextInput value={this.state.feeling}
                  onFocus={() => this.setState({feeling: ''})}
                  onChangeText={(feeling) => this.setState({feeling})}/>
          </View>
          <View>
            <CustomButton name='RETURN' onPress={() => this.props.navigator.pop()}/>
            <CustomButton name='OK' onPress={() => this.addItem()}/>
          </View>
        </View>
    );
  }
}
