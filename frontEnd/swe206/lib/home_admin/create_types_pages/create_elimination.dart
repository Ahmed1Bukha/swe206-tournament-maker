import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';

class CreateEleminationPage extends StatelessWidget {
  const CreateEleminationPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Create elimination tourmanent"),
      ),
      body: Column(
        children: [listDropDown()],
      ),
    );
  }
}

class listDropDown extends StatefulWidget {
  listDropDown({super.key});
  List<String> list = <String>['One', 'Two', 'Three', 'Four'];
  @override
  State<listDropDown> createState() => _listDropDownState();
}

class _listDropDownState extends State<listDropDown> {
  @override
  Widget build(BuildContext context) {
    String dropdownValue = widget.list.first;
    return DropdownButton<String>(
      value: dropdownValue,
      icon: const Icon(Icons.arrow_downward),
      elevation: 16,
      style: const TextStyle(color: Colors.deepPurple),
      underline: Container(
        height: 2,
        color: Colors.deepPurpleAccent,
      ),
      onChanged: (String? value) {
        // This is called when the user selects an item.
        setState(() {
          dropdownValue = value!;
        });
      },
      items: widget.list.map<DropdownMenuItem<String>>((String value) {
        return DropdownMenuItem<String>(
          value: value,
          child: Text(value),
        );
      }).toList(),
    );
  }
}
