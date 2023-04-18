// ignore_for_file: no_leading_underscores_for_local_identifiers

import 'package:flutter/material.dart';
import 'package:swe206/home_admin/main_page_admin.dart';

class AuthPageAdmin extends StatelessWidget {
  const AuthPageAdmin({super.key});

  @override
  Widget build(BuildContext context) {
    String _adminID = '';
    String _adminPass = '';
    bool isLoading;

    return Scaffold(
      appBar: AppBar(
        title: const Text("Authentication page admin"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              "Admin ID",
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
                hintText: "Enter your Admin ID",
                border: OutlineInputBorder(),
              ),
              onChanged: (value) {
                _adminID = value;
              },
            ),
            const SizedBox(
              height: 10,
            ),
            const Text(
              "Admin Password",
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
              obscureText: true,
              enableSuggestions: false,
              autocorrect: false,
              decoration: const InputDecoration(
                hintText: "Enter your Admin password",
                border: OutlineInputBorder(),
              ),
              onChanged: (value) {
                _adminPass = value;
              },
            ),
            Center(
                child: TextButton(
                    onPressed: () {
                      print(_adminID);
                      print(_adminPass);
                      Navigator.pushReplacementNamed(context, MainPageAdmin.id);
                    },
                    child: const Text("Authinticate")))
          ],
        ),
      ),
    );
  }
}
