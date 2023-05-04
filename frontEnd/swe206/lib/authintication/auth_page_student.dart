import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:flutter_svg/svg.dart';
import 'package:swe206/classes/controller_student.dart';
import 'package:swe206/authintication/auth_page_admin.dart';
import 'package:swe206/home_student/home_page_student.dart';
import 'package:swe206/home_student/main_page_student.dart';

import '../UI_componenets/tournament_card_student.dart';

class AuthPageStudent extends StatefulWidget {
  AuthPageStudent({super.key});
  static String id = "AuthPage";

  @override
  State<AuthPageStudent> createState() => _AuthPageStudentState();
}

class _AuthPageStudentState extends State<AuthPageStudent> {
  bool _isLoading = true;
  String _studentId = "";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              GestureDetector(
                onTap: () {
                  Navigator.pop(context);
                },
                child: Icon(
                  Icons.arrow_back_ios,
                  size: 30,
                ),
              ),
              const Center(
                child: Text(
                  "Student Auth",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    color: Colors.blue,
                    fontSize: 60,
                    fontFamily: "Mplus",
                    fontWeight: FontWeight.w700,
                  ),
                ),
              ),
              SizedBox(
                height: 40,
              ),
              Center(
                child: SvgPicture.asset(
                  "lib/assets/svg/student-login.svg",
                  width: MediaQuery.of(context).size.width - 200,
                ),
              ),
              SizedBox(
                height: 100,
              ),
              TextFormField(
                cursorColor: Colors.black,
                style: const TextStyle(
                  color: Colors.black,
                  fontSize: 20,
                ),
                decoration: InputDecoration(
                  prefixIcon: Icon(Icons.person),
                  hintText: "Entet UserName",
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20),
                    borderSide: BorderSide.none,
                  ),
                ),
              ),
              SizedBox(
                height: 20,
              ),
              TextFormField(
                obscureText: true,
                cursorColor: Colors.black,
                style: const TextStyle(
                  color: Colors.black,
                  fontSize: 20,
                ),
                decoration: InputDecoration(
                  prefixIcon: Icon(
                    Icons.password,
                  ),
                  hintText: "Entet Password",
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20),
                    borderSide: BorderSide.none,
                  ),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              Center(
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.pushReplacementNamed(context, MainPageStudent.id);
                  },
                  child: const Text(
                    "Sign In",
                    style: TextStyle(
                      fontSize: 30,
                    ),
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
