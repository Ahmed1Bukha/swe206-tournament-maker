import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/classes/tournamentsManager.dart';
import 'package:swe206/authintication/auth_page_admin.dart';
import 'package:swe206/home_student/home_page_student.dart';
import 'package:swe206/home_student/main_page_student.dart';

import '../UI_componenets/tournament_card.dart';

class AuthPage extends StatefulWidget {
  AuthPage({super.key});
  static String id = "AuthPage";

  @override
  State<AuthPage> createState() => _AuthPageState();
}

class _AuthPageState extends State<AuthPage> {
  bool _isLoading = true;
  String _studentId = "";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        child: Text("Admin "),
        onPressed: () {
          Navigator.pushReplacement(context,
              MaterialPageRoute(builder: (context) => const AuthPageAdmin()));
        },
      ),
      appBar: AppBar(
        title: const Text("Authentication"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              "Student ID",
              style: TextStyle(
                fontSize: 25,
                fontStyle: FontStyle.italic,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            TextField(
              decoration: const InputDecoration(
                hintText: "Enter your student ID",
                border: OutlineInputBorder(),
              ),
              onChanged: (value) {
                _studentId = value;
              },
            ),
            Center(
                child: TextButton(
                    onPressed: () {
                      Navigator.pushReplacementNamed(
                          context, MainPageStudent.id);
                    },
                    child: const Text("Authinticate")))
          ],
        ),
      ),
    );
  }
}
