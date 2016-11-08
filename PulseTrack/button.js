import React, { Component } from 'react';
import {
  Text,
  TouchableHighlight,
} from 'react-native';

export class CustomButton extends Component {
  render() {
    return(
      <TouchableHighlight onPress={this.props.onPress}>
        <Text>{this.props.name}</Text>
      </TouchableHighlight>
    )
  }
}
