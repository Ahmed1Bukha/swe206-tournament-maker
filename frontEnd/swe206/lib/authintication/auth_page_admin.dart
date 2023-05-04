// ignore_for_file: no_leading_underscores_for_local_identifiers

import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:swe206/home_admin/main_page_admin.dart';

class AuthPageAdmin extends StatelessWidget {
  const AuthPageAdmin({super.key});
  static String id = "AuthPageAdmin";
  @override
  Widget build(BuildContext context) {
    String _adminID = '';
    String _adminPass = '';
    bool isLoading;

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
                  "Admin Auth",
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
                  "lib/assets/svg/icon-admin.svg",
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
              SizedBox(
                height: 20,
              ),
              Center(
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.pushReplacementNamed(context, MainPageAdmin.id);
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
