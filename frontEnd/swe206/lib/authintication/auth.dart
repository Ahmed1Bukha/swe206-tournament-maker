import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/home_student/home_page_student.dart';

class AuthPage extends StatefulWidget {
  const AuthPage({super.key});
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
                      debugPrint("Student id is: " + _studentId);
                      Navigator.pushReplacementNamed(
                          context, HomePageStudent.id);
                    },
                    child: const Text("Authinticate")))
          ],
        ),
      ),
    );
  }
}
