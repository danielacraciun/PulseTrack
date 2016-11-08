import React, { Component } from 'react';
import {
  Text,
  TextInput,
  Navigator,
  View
} from 'react-native';
import {Button} from './button.js'

export class AddItem extends Component {
  constructor(props) {
    super(props);
    this.state = { text: '<pulse value>' };
  }
  addItem() {
    // todo: error message if empty fields
    // Saving is done here
    // if (this.state.text !== '')
    // {
    //
    // }
    // Then return to first page after inserting
    this.props.navigator.pop();
  }

  render() {
    return(
      <View>
        <Text>LOG PULSE</Text>
        <View>
          <TextInput value={this.state.text} onFocus={() => this.setState({text: ''})} onChangeText={(text) => this.setState({text})}/>
        </View>
        <View>
          <Button name='RETURN' onPress={() => this.props.navigator.pop()}/>
          <Button name='OK' onPress={() => this.addItem()}/>
        </View>
      </View>
    );
  }
}
