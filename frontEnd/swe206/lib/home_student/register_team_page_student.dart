// ignore_for_file: use_build_context_synchronously

import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/const.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';

import '../requests.dart';

//TODO Ask the boiz how they implemented register stuff.
class RegisterTeamStudent extends StatelessWidget {
  RegisterTeamStudent(this.tournament, {super.key});
  final TournamentCardStudent tournament;

  List<Widget> texts = [];
  List<TextEditingController> controllers = [];
  final snackBarRegistering = SnackBar(
    content: const Text('Processing'),
    action: SnackBarAction(
      label: 'Done',
      onPressed: () {
        // Some code to undo the change.
      },
    ),
  );
  final doneRegisterSnack = SnackBar(
    content: const Text('Done Registering.'),
    action: SnackBarAction(
      label: 'Done',
      onPressed: () {
        // Some code to undo the change.
      },
    ),
  );
  final alreadyRegistered = SnackBar(
    content: const Text('This ID is already registered to this tournament.'),
    action: SnackBarAction(
      label: 'Done',
      onPressed: () {
        // Some code to undo the change.
      },
    ),
  );
  getTextFields() {
    for (int i = 0; i < tournament.numberOfParticipant - 1; i++) {
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
                const Text(
                  "Only number Team-mates' ID",
                  style: TextStyle(fontSize: 14),
                ),
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
                  onPressed: () async {
                    if (_formKey.currentState!.validate()) {
                      List<int> ids = [];
                      for (var id in controllers) {
                        ids.add(int.parse(id.text));
                      }
                      ScaffoldMessenger.of(context)
                          .showSnackBar(snackBarRegistering);

                      var response = await Requests.addTeam(
                          myTeamNameController.text, tournament.id, ids);

                      ScaffoldMessenger.of(context).clearSnackBars();

                      if (response == "done") {
                        ScaffoldMessenger.of(context)
                            .showSnackBar(doneRegisterSnack);
                      } else if (response == "registered") {
                        ScaffoldMessenger.of(context)
                            .showSnackBar(alreadyRegistered);
                      }

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
