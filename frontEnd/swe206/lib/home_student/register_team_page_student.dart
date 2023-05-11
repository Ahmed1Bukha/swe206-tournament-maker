import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/const.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';

//TODO Ask the boiz how they implemented register stuff.
class RegisterTeamStudent extends StatelessWidget {
  RegisterTeamStudent(this.tournament, {super.key});
  final TournamentCardStudent tournament;

  List<Widget> texts = [];
  List<TextEditingController> controllers = [];
  getTextFields() {
    for (int i = 0; i < tournament.numberOfParticipant; i++) {
      controllers.add(TextEditingController());
      texts.add(
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: TextFormField(
            controller: controllers[i],
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter some number';
              }
              return null;
            },
            cursorColor: Colors.black,
            style: const TextStyle(
              color: Colors.black,
              fontSize: 20,
            ),
            decoration: InputDecoration(
              prefixIcon: Icon(Icons.person),
              hintText: "Enter ID member ${i + 1}",
              filled: true,
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(20),
                borderSide: BorderSide.none,
              ),
            ),
          ),
        ),
      );
    }

    return texts;
  }

  @override
  Widget build(BuildContext context) {
    final _formKey = GlobalKey<FormState>();
    TextEditingController myTeamNameController = TextEditingController();
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(color: Colors.black),
        elevation: 0,
        backgroundColor: Colors.white,
        title: Text(
          "Register team",
          style: h2,
        ),
      ),
      body: Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: TextFormField(
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter some text';
                      }
                      return null;
                    },
                    cursorColor: Colors.black,
                    style: const TextStyle(
                      color: Colors.black,
                      fontSize: 20,
                    ),
                    decoration: InputDecoration(
                      prefixIcon: Icon(Icons.assistant_photo_rounded),
                      hintText: "Entet Team Name",
                      filled: true,
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20),
                        borderSide: BorderSide.none,
                      ),
                    ),
                    controller: myTeamNameController,
                  ),
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
