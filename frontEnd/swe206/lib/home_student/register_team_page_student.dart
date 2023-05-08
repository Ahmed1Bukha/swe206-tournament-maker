import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';

//TODO Ask the boiz how they implemented register stuff.
class RegisterTeamStudent extends StatelessWidget {
  RegisterTeamStudent(this.tournament, {super.key});
  final TournamentCardStudent tournament;

  List<TextFormField> texts = [];
  List<TextEditingController> controllers = [];
  List<TextFormField> getTextFields() {
    for (int i = 0; i < tournament.numberOfParticipant; i++) {
      controllers.add(TextEditingController());
      texts.add(TextFormField(
        controller: controllers[i],
        validator: (value) {
          if (value == null || value.isEmpty) {
            return 'Please enter some number';
          }
          return null;
        },
        keyboardType: TextInputType.number,
        decoration: InputDecoration(
          label: Text("Team member ${i + 1} ID"),
          icon: const Icon(Icons.person),
          hintText: "Enter ID",
        ),
      ));
    }

    return texts;
  }

  @override
  Widget build(BuildContext context) {
    final _formKey = GlobalKey<FormState>();
    TextEditingController myTeamNameController = TextEditingController();
    return Scaffold(
      appBar: AppBar(
        title: const Text("Register team page"),
      ),
      body: Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              children: [
                TextFormField(
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter some text';
                    }
                    return null;
                  },
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(
                    label: Text("Team Name"),
                    icon: Icon(Icons.near_me),
                    hintText: "Enter Team Name",
                  ),
                  controller: myTeamNameController,
                ),
                ...getTextFields(),
                ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      for (int i = 0; i < controllers.length; i++) {
                        print(controllers[i].text);
                      }
                      print(myTeamNameController.text);
                      Navigator.of(context).popUntil((route) => route.isFirst);
                    }
                  },
                  child: Text("Register"),
                )
              ],
            ),
          )),
    );
  }
}
