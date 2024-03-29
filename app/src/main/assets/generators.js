/*
 *  Copyright 2015 Google Inc. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * @fileoverview Generators for the Turtle Blockly demo on Android.
 * @author fenichel@google.com (Rachel Fenichel)
 */
'use strict';

// Extensions to Blockly's language and JavaScript generator.
Blockly.JavaScript['turtle_move_internal'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = block.getFieldValue('VALUE');
  return 'Turtle.' + block.getFieldValue('DIR') +
      '(' + value + ', \'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_turn_internal'] = function(block) {
  // Generate JavaScript for turning left or right.
  var value = block.getFieldValue('VALUE');
  return 'Turtle.' + block.getFieldValue('DIR') +
      '(' + value + ', \'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_colour_internal'] = function(block) {
  // Generate JavaScript for setting the colour.
  var colour = block.getFieldValue('COLOUR');
  return 'Turtle.penColour(\'' + colour + '\', \'block_id_' +
      block.id + '\');\n';
};

Blockly.JavaScript['turtle_pen'] = function(block) {
  // Generate JavaScript for pen up/down.
  return 'Turtle.' + block.getFieldValue('PEN') +
      '(\'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_width'] = function(block) {
  // Generate JavaScript for setting the width.
  var width = Blockly.JavaScript.valueToCode(block, 'WIDTH',
      Blockly.JavaScript.ORDER_NONE) || '1';
  return 'Turtle.penWidth(' + width + ', \'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_visibility'] = function(block) {
  // Generate JavaScript for changing turtle visibility.
  return 'Turtle.' + block.getFieldValue('VISIBILITY') +
      '(\'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_print'] = function(block) {
  // Generate JavaScript for printing text.
  var argument0 = String(Blockly.JavaScript.valueToCode(block, 'TEXT',
      Blockly.JavaScript.ORDER_NONE) || '\'\'');
  return 'Turtle.drawPrint(' + argument0 + ', \'block_id_' +
      block.id + '\');\n';
};

Blockly.JavaScript['turtle_font'] = function(block) {
  // Generate JavaScript for setting the font.
  return 'Turtle.drawFont(\'' + block.getFieldValue('FONT') + '\',' +
      Number(block.getFieldValue('FONTSIZE')) + ',\'' +
      block.getFieldValue('FONTSTYLE') + '\', \'block_id_' +
      block.id + '\');\n';
};

Blockly.JavaScript['turtle_move'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = Blockly.JavaScript.valueToCode(block, 'VALUE',
      Blockly.JavaScript.ORDER_NONE) || '0';
  return 'Turtle.' + block.getFieldValue('DIR') +
      '(' + value + ', \'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_turn'] = function(block) {
  // Generate JavaScript for turning left or right.
  var value = Blockly.JavaScript.valueToCode(block, 'VALUE',
      Blockly.JavaScript.ORDER_NONE) || '0';
  return 'Turtle.' + block.getFieldValue('DIR') +
      '(' + value + ', \'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_width'] = function(block) {
  // Generate JavaScript for setting the width.
  var width = Blockly.JavaScript.valueToCode(block, 'WIDTH',
      Blockly.JavaScript.ORDER_NONE) || '1';
  return 'Turtle.penWidth(' + width + ', \'block_id_' + block.id + '\');\n';
};

Blockly.JavaScript['turtle_colour'] = function(block) {
  // Generate JavaScript for setting the colour.
  var colour = Blockly.JavaScript.valueToCode(block, 'COLOUR',
      Blockly.JavaScript.ORDER_NONE) || '\'#000000\'';
  return 'Turtle.penColour(' + colour + ', \'block_id_' +
      block.id + '\');\n';
};


Blockly.JavaScript['move_right'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'moveRight();\n';
  return code;
};

Blockly.JavaScript['move_left'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'moveLeft();\n';
  return code;
};

Blockly.JavaScript['move_up'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'moveUp();\n';
  return code;
};

Blockly.JavaScript['move_down'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'moveDown();\n';
  return code;
};

Blockly.JavaScript['pickup'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'pickUpCoin();\n';
  return code;
};

Blockly.JavaScript['dummy_while'] = function(block) {
  var statements_movements = Blockly.JavaScript.statementToCode(block, 'movements');
  // TODO: Assemble JavaScript into code variable.
  return statements_movements;
};


Blockly.JavaScript['turtle_repeat_internal'] = Blockly.JavaScript['controls_repeat'];


/**
 * The generated code for turtle blocks includes block ID strings.  These are useful for
 * highlighting the currently running block, but that behaviour is not supported in Android Blockly
 * as of May 2016.  This snippet generates the block code normally, then strips out the block IDs
 * for readability when displaying the code to the user.
 *
 * Post-processing the block code in this way allows us to use the same generators for the Android
 * and web versions of the turtle.
 */
Blockly.JavaScript.workspaceToCodeWithId = Blockly.JavaScript.workspaceToCode;

Blockly.JavaScript.workspaceToCode = function(workspace) {
  var code = this.workspaceToCodeWithId(workspace);
  // Strip out block IDs for readability.
  code = goog.string.trimRight(code.replace(/(,\s*)?'block_id_[^']+'\)/g, ')'))
  return code;
};









//////////////// ARDUINO GENERATORS//////////

Blockly.JavaScript['setup'] = function(block) {
  var statements_name = Blockly.JavaScript.statementToCode(block, 'NAME');
  var code = '1' + statements_name;
  return code;
};

Blockly.JavaScript['loop'] = function(block) {
  var statements_loop = Blockly.JavaScript.statementToCode(block, 'loop');
  // TODO: Assemble JavaScript into code variable.
  var code = '2' + statements_loop;
  return code;
};

Blockly.JavaScript['pinmodeoutput13'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '3';
  return code;
};

Blockly.JavaScript['led_on'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '4';
  return code;
};

Blockly.JavaScript['led_off'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '5';
  return code;
};

Blockly.JavaScript['delay_1000'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '6';
  return code;
};

Blockly.JavaScript['attach_button'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '7';
  return code;
};

Blockly.JavaScript['attach_led'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '8';
  return code;
};

Blockly.JavaScript['if_button_pressed'] = function(block) {
  var statements_action = Blockly.JavaScript.statementToCode(block, 'ACTION');
  var statements_else_action = Blockly.JavaScript.statementToCode(block, 'ELSE ACTION');
  // TODO: Assemble JavaScript into code variable.
  var code = '9' + statements_action + statements_else_action;
  return code;
};

Blockly.JavaScript['delay_200'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'a';
  return code;
};

Blockly.JavaScript['for_pin_2to5'] = function(block) {
  var statements_pinaction = Blockly.JavaScript.statementToCode(block, 'pinACTION');
  // TODO: Assemble JavaScript into code variable.
  var code = 'b' + statements_pinaction;
  return code;
};

Blockly.JavaScript['attach_pot'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'c';
  return code;
};

Blockly.JavaScript['write_to_led'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'd';
  return code;
};

Blockly.JavaScript['read_pot'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'e';
  return code;
};

Blockly.JavaScript['if_water_val'] = function(block) {
  var statements_if = Blockly.JavaScript.statementToCode(block, 'if');
  var statements_else = Blockly.JavaScript.statementToCode(block, 'else');
  // TODO: Assemble JavaScript into code variable.
  var code = 'f' + statements_if + statements_else;
  return code;
};

Blockly.JavaScript['buzzer_on'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'g';
  return code;
};

Blockly.JavaScript['buzzer_off'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'h';
  return code;
};

Blockly.JavaScript['set_color_red'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'i';
  return code;
};

Blockly.JavaScript['set_color_green'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'j';
  return code;
};

Blockly.JavaScript['set_color_blue'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'k';
  return code;
};

Blockly.JavaScript['if_potentiameter_threshhold'] = function(block) {
  var number_pot_val = block.getFieldValue('Pot_val');
  if(number_pot_val != 400){
    return "";}
  var statements_if = Blockly.JavaScript.statementToCode(block, 'if');
  var statements_else = Blockly.JavaScript.statementToCode(block, 'else');
  // TODO: Assemble JavaScript into code variable.
  var code = 'l' + statements_if + statements_else;
  return code;
};

Blockly.JavaScript['attach_servo'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'm';
  return code;
};

Blockly.JavaScript['write_servo'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'n';
  return code;
};

Blockly.JavaScript['read_joys'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'o';
  return code;
};

Blockly.JavaScript['calc_temp_pin'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'p';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['for_temp_pin'] = function(block) {
  var value_temp = Blockly.JavaScript.valueToCode(block, 'TEMP', Blockly.JavaScript.ORDER_ATOMIC);
  var statements_do = Blockly.JavaScript.statementToCode(block, 'DO');
  // TODO: Assemble JavaScript into code variable.
  var code = 'q' + value_temp + statements_do;
  return code;
};

Blockly.JavaScript['for_pin_2to6'] = function(block) {
  var statements_pinaction = Blockly.JavaScript.statementToCode(block, 'pinACTION');
  // TODO: Assemble JavaScript into code variable.
  var code = 'r' + statements_pinaction;
  return code;
};


