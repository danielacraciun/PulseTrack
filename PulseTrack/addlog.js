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
}
